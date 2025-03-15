package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
open class UidStringIdTable(name: String = "", columnName: String = "id") : IdTable<Uuid>(name) {
    final override val id: Column<EntityID<Uuid>> =
        varchar(columnName, 32).transform({ Uuid.Companion.parseHex(it) }, { it.toHexString() }).entityId()

    final override val primaryKey: PrimaryKey = PrimaryKey(id)
}