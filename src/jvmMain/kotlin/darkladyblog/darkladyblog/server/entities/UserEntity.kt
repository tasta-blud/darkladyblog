package darkladyblog.darkladyblog.server.entities

import darkladyblog.darkladyblog.common.model.Sex
import darkladyblog.darkladyblog.server.db.Users
import org.jetbrains.exposed.dao.ULongEntity
import org.jetbrains.exposed.dao.ULongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<UserEntity>(Users)

    var username: String by Users.username
    val password: String by Users.password
    val nick: String by Users.nick
    val email: String by Users.email
    val sex: Sex by Users.sex
    val title: String by Users.title
    val descriptionShortSource: String by Users.descriptionShortSource
    val descriptionShortCompiled: String by Users.descriptionShortCompiled
    val descriptionLongSource: String by Users.descriptionLongSource
    val descriptionLongCompiled: String by Users.descriptionLongCompiled
}
