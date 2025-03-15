package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

interface OwnedTable<ID : Any> : IdentifiedTable<ID> {
    val createdBy: Column<EntityID<ID>>
}