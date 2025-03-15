package darkladyblog.darkladyblog.common.base

import darkladyblog.darkladyblog.common.model.UserModel

interface Owned<ID : Any> : IdModel<ID> {
    val createdBy: UserModel
}