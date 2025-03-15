
package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
open class UidIdTable(name: String = "", columnName: String = "id") : IdTable<Uuid>(name) {
    final override val id: Column<EntityID<Uuid>> =
        uuid(columnName).transform({ it.toKotlinUuid() }, { it.toJavaUuid() }).entityId()

    final override val primaryKey: PrimaryKey = PrimaryKey(id)
}