package darkladyblog.darkladyblog.server.repositories

import darkladyblog.darkladyblog.common.config.Formats
import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.common.util.toLocalizedString
import darkladyblog.darkladyblog.server.base.Repository
import darkladyblog.darkladyblog.server.db.Blogs
import darkladyblog.darkladyblog.server.db.Topics
import darkladyblog.darkladyblog.server.db.Users
import darkladyblog.darkladyblog.server.util.search
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.koin.core.annotation.Single

@Single
class TopicRepository(private val userRepository: UserRepository, private val blogRepository: BlogRepository) :
    Repository<Topics, ULong, TopicModel>(Topics) {

    init {
        db.transactionally {
            SchemaUtils.create(Topics)
        }
    }

    private val createdByAlias = Users.alias("createdBy")
    private val updatedByAlias = Users.alias("updatedBy")
    private val blogAlias = Blogs.alias("blog")

    override fun selectRows(table: Topics, alias: Alias<Topics>): Query =
        alias
            .leftJoin(createdByAlias, onColumn = { alias[table.createdBy] }, otherColumn = { createdByAlias[Users.id] })
            .leftJoin(updatedByAlias, onColumn = { alias[table.updatedBy] }, otherColumn = { createdByAlias[Users.id] })
            .leftJoin(blogAlias, onColumn = { alias[table.blog] }, otherColumn = { blogAlias[Blogs.id] })
            .selectAll()

    override fun fromRow(row: ResultRow, table: Topics, alias: Alias<Topics>): TopicModel =
        TopicModel(
            blogRepository.fromRow(row, Blogs, blogAlias),
            row[alias[table.title]],
            row[alias[table.descriptionShortSource]],
            row[alias[table.descriptionShortCompiled]],
            row[alias[table.descriptionLongSource]],
            row[alias[table.descriptionLongCompiled]],
            row[alias[table.alias]],
            userRepository.fromRow(row, Users, createdByAlias),
            row[alias[table.createdAt]],
            row[alias[table.createdAt]].toLocalizedString(Formats.userFormat),
            row[alias[table.updatedBy]]?.value?.let { userRepository.fromRow(row, Users, updatedByAlias) },
            row[alias[table.updatedAt]],
            row[alias[table.updatedAt]]?.toLocalizedString(Formats.userFormat) ?: "",
            row[alias[table.id]].value
        )

    override fun updating(table: Topics, it: UpdateBuilder<Int>, model: TopicModel) {
        it[table.blog] = EntityID(model.blog.id ?: return, Blogs)
        it[table.title] = model.title
        it[table.descriptionShortSource] = model.descriptionShortSource
        it[table.descriptionShortCompiled] = model.descriptionShortCompiled
        it[table.descriptionLongSource] = model.descriptionLongSource
        it[table.descriptionLongCompiled] = model.descriptionLongCompiled
        it[table.alias] = model.alias
        it[table.createdBy] = EntityID(model.createdBy.id ?: return, Users)
        it[table.createdAt] = model.createdAt
        model.updatedBy?.let { updatedByRef -> it[table.updatedBy] = EntityID(updatedByRef.id ?: return@let, Users) }
        it[table.updatedAt] = model.updatedAt
    }

    fun all(
        blogId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(alias[table.id] to SortOrder.ASC),
    ): List<TopicModel> =
        all(offset, limit, *order) { alias[table.blog] eq blogId }


    fun count(blogId: ULong): Long =
        count { alias[table.blog] eq blogId }

    override fun searching(query: String, it: SqlExpressionBuilder, table: Topics, alias: Alias<Topics>): Op<Boolean> =
        it.run {
            query.let {
                (alias[table.alias] search it) or (alias[table.title] search it) or (alias[table.descriptionLongSource] search it)
            }
        }
}
