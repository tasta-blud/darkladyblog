package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

open class StringIdTable(name: String = "", columnName: String = "id", length: Int = 255) : IdTable<String>(name) {
    final override val id: Column<EntityID<String>> = varchar(columnName, length).run {
        clientDefault { UUID.randomUUID().toString().replace("-", "") }
    }.entityId()
    final override val primaryKey: PrimaryKey = PrimaryKey(id)
}