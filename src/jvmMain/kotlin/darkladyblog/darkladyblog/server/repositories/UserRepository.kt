package darkladyblog.darkladyblog.server.repositories

import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.base.Repository
import darkladyblog.darkladyblog.server.db.Users
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.koin.core.annotation.Single

@Single
class UserRepository() : Repository<Users, ULong, UserModel>(Users) {

    init {
        db.transactionally {
            SchemaUtils.create(Users)
        }
    }

    override fun fromRow(row: ResultRow, table: Users, alias: Alias<Users>): UserModel =
        UserModel(
            row[alias[table.username]],
            row[alias[table.password]],
            row[alias[table.nick]],
            row[alias[table.email]],
            row[alias[table.sex]],
            row[alias[table.title]],
            row[alias[table.descriptionShortSource]],
            row[alias[table.descriptionShortCompiled]],
            row[alias[table.descriptionLongSource]],
            row[alias[table.descriptionLongCompiled]],
            row[alias[table.id]].value
        )

    override fun updating(table: Users, it: UpdateBuilder<Int>, model: UserModel) {
        it[table.username] = model.username
        it[table.password] = model.password
        it[table.nick] = model.nick
        it[table.email] = model.email
        it[table.sex] = model.sex
        it[table.title] = model.title
        it[table.descriptionShortSource] = model.descriptionShortSource
        it[table.descriptionShortCompiled] = model.descriptionShortCompiled
        it[table.descriptionLongSource] = model.descriptionLongSource
        it[table.descriptionLongCompiled] = model.descriptionLongCompiled
    }

}
