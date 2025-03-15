package darkladyblog.darkladyblog.server.repositories

import darkladyblog.darkladyblog.common.config.Formats
import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.util.toLocalizedString
import darkladyblog.darkladyblog.server.base.Repository
import darkladyblog.darkladyblog.server.db.Blogs
import darkladyblog.darkladyblog.server.db.Comments
import darkladyblog.darkladyblog.server.db.Topics
import darkladyblog.darkladyblog.server.db.Users
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.koin.core.annotation.Single

@Single
class CommentRepository(private val userRepository: UserRepository, private val topicRepository: TopicRepository) :
    Repository<Comments, ULong, CommentModel>(Comments) {

    init {
        db.transactionally {
            SchemaUtils.create(Comments)
        }
    }

    private val createdByAlias = Users.alias("createdBy")
    private val updatedByAlias = Users.alias("updatedBy")
    private val parentAlias = table.alias("parent")
    private val topicAlias = Topics.alias("topic")
    private val blogAlias = Blogs.alias("blog")

    override fun selectRows(table: Comments, alias: Alias<Comments>): Query =
        alias
            .leftJoin(createdByAlias, onColumn = { alias[table.createdBy] }, otherColumn = { createdByAlias[Users.id] })
            .leftJoin(updatedByAlias, onColumn = { alias[table.updatedBy] }, otherColumn = { createdByAlias[Users.id] })
            .leftJoin(parentAlias, onColumn = { alias[table.parent] }, otherColumn = { parentAlias[table.id] })
            .leftJoin(topicAlias, onColumn = { alias[table.topic] }, otherColumn = { topicAlias[Topics.id] })
            .leftJoin(blogAlias, onColumn = { topicAlias[Topics.blog] }, otherColumn = { blogAlias[Blogs.id] })
            .selectAll()

    override fun fromRow(row: ResultRow, table: Comments, alias: Alias<Comments>): CommentModel =
        CommentModel(
            topicRepository.fromRow(row, Topics, topicAlias),
            row[alias[table.parent]]?.let { fromRow(row, Comments, parentAlias) },
            row[alias[table.title]],
            row[alias[table.descriptionShortSource]],
            row[alias[table.descriptionShortCompiled]],
            row[alias[table.descriptionLongSource]],
            row[alias[table.descriptionLongCompiled]],
            userRepository.fromRow(row, Users, createdByAlias),
            row[alias[table.createdAt]],
            row[alias[table.createdAt]].toLocalizedString(Formats.userFormat),
            row[alias[table.updatedBy]]?.value?.let { userRepository.fromRow(row, Users, updatedByAlias) },
            row[alias[table.updatedAt]],
            row[alias[table.updatedAt]]?.toLocalizedString(Formats.userFormat) ?: "",
            row[alias[table.id]].value
        )

    override fun updating(table: Comments, it: UpdateBuilder<Int>, model: CommentModel) {
        it[table.topic] = EntityID(model.topic.id!!, Topics)
        model.parent?.let { parentRef -> it[table.parent] = EntityID(parentRef.id!!, Comments) }
        it[table.title] = model.title
        it[table.descriptionShortSource] = model.descriptionShortSource
        it[table.descriptionShortCompiled] = model.descriptionShortCompiled
        it[table.descriptionLongSource] = model.descriptionLongSource
        it[table.descriptionLongCompiled] = model.descriptionLongCompiled
        it[table.createdBy] = EntityID(model.createdBy.id!!, Users)
        it[table.createdAt] = model.createdAt
        model.updatedBy?.let { updatedByRef -> it[table.updatedBy] = EntityID(updatedByRef.id!!, Users) }
        it[table.updatedAt] = model.updatedAt
    }

    fun all(
        topicId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(alias[table.id] to SortOrder.ASC),
    ): List<CommentModel> =
        all(offset, limit, *order) { alias[table.topic] eq topicId }

    fun all(
        topicId: ULong,
        commentId: ULong? = null,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(alias[table.id] to SortOrder.ASC),
    ): List<CommentModel> =
        all(offset, limit, *order) {
            (alias[table.topic] eq topicId).and {
                if (commentId != null) alias[table.parent] eq commentId else alias[table.parent] eq null
            }
        }

    fun count(topicId: ULong): Long =
        count { alias[table.topic] eq topicId }

    fun count(topicId: ULong, commentId: ULong? = null): Long =
        count {
            (alias[table.topic] eq topicId).and {
                if (commentId != null) alias[table.parent] eq commentId else alias[table.parent] eq null
            }
        }

}
