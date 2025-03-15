package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.dao.id.EntityID

interface IdEntity<ID : Any> {
    val id: EntityID<ID>
}
