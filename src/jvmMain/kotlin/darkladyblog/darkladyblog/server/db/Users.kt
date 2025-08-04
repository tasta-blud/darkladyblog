package darkladyblog.darkladyblog.server.db

import darkladyblog.darkladyblog.common.model.Sex
import darkladyblog.darkladyblog.server.data.DescribedTable
import org.jetbrains.exposed.dao.id.ULongIdTable
import org.jetbrains.exposed.sql.Column

object Users : ULongIdTable("users"), DescribedTable<ULong> {
    val username: Column<String> = varchar("username", length = 255).uniqueIndex()
    val password: Column<String> = varchar("password", length = 255)
    val nick: Column<String> = varchar("nick", length = 255)
    val email: Column<String> = varchar("email", length = 255)
    val sex: Column<Sex> = enumerationByName<Sex>("sex", 10)
    override val title: Column<String> = varchar("title", length = 255)
    override val descriptionShortSource: Column<String> = varchar("description_short_source", length = 255)
    override val descriptionShortCompiled: Column<String> = varchar("description_short_compiled", length = 255)
    override val descriptionLongSource: Column<String> = text("description_long_source", eagerLoading = true)
    override val descriptionLongCompiled: Column<String> = text("description_long_compiled", eagerLoading = true)
}
