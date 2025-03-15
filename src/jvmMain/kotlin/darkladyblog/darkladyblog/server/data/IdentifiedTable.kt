package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

interface IdentifiedTable<ID : Any> {
    val id: Column<EntityID<ID>>
}