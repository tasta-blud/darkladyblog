package darkladyblog.darkladyblog.server.data

import darkladyblog.darkladyblog.server.entities.UserEntity

interface OwnedEntity<ID : Any> : IdEntity<ID> {
    val createdBy: UserEntity
}