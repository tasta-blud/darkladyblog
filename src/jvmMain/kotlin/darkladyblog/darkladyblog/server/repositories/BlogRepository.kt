package darkladyblog.darkladyblog.server.repositories

import darkladyblog.darkladyblog.common.config.Formats
import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.util.toLocalizedString
import darkladyblog.darkladyblog.server.base.Repository
import darkladyblog.darkladyblog.server.db.Blogs
import darkladyblog.darkladyblog.server.db.Users
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.koin.core.annotation.Single

@Single
class BlogRepository(private val userRepository: UserRepository) : Repository<Blogs, ULong, BlogModel>(Blogs) {

    init {
        db.transactionally {
            SchemaUtils.create(Blogs)
        }
    }

    private val createdByAlias = Users.alias("createdBy")
    private val updatedByAlias = Users.alias("updatedBy")

    override fun selectRows(table: Blogs, alias: Alias<Blogs>): Query =
        alias
            .leftJoin(createdByAlias, onColumn = { alias[table.createdBy] }, otherColumn = { createdByAlias[Users.id] })
            .leftJoin(updatedByAlias, onColumn = { alias[table.updatedBy] }, otherColumn = { createdByAlias[Users.id] })
            .selectAll()

    override fun fromRow(row: ResultRow, table: Blogs, alias: Alias<Blogs>): BlogModel {
        return BlogModel(
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
    }

    override fun updating(table: Blogs, it: UpdateBuilder<Int>, model: BlogModel) {
        it[table.title] = model.title
        it[table.descriptionShortSource] = model.descriptionShortSource
        it[table.descriptionShortCompiled] = model.descriptionShortCompiled
        it[table.descriptionLongSource] = model.descriptionLongSource
        it[table.descriptionLongCompiled] = model.descriptionLongCompiled
        it[table.alias] = model.alias
        it[table.createdBy] = EntityID(model.createdBy.id!!, Users)
        it[table.createdAt] = model.createdAt
        model.updatedBy?.let { updatedByRef -> it[table.updatedBy] = EntityID(updatedByRef.id!!, Users) }
        it[table.updatedAt] = model.updatedAt
    }

}
