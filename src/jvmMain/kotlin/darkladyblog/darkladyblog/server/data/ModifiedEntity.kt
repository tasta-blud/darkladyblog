package darkladyblog.darkladyblog.server.data

import darkladyblog.darkladyblog.server.entities.UserEntity
import kotlinx.datetime.LocalDateTime

interface ModifiedEntity<ID : Any> : OwnedEntity<ID> {
    override var createdBy: UserEntity
    var createdAt: LocalDateTime
    var updatedBy: UserEntity?
    var updatedAt: LocalDateTime?
}