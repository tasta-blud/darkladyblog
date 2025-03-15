package darkladyblog.darkladyblog.common.base

import darkladyblog.darkladyblog.common.model.UserModel
import kotlinx.datetime.LocalDateTime

interface Modified<ID : Any> : Owned<ID> {
    override val createdBy: UserModel
    val createdAt: LocalDateTime
    val createdAtString: String
    val updatedBy: UserModel?
    val updatedAt: LocalDateTime?
    val updatedAtString: String
}