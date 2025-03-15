package darkladyblog.darkladyblog.common.model

import darkladyblog.darkladyblog.common.base.ULongIdModel
import dev.fritz2.core.Lenses
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Lenses
@Serializable
data class UserModel(
    val username: String,
    @Transient
    val password: String = "",
    val nick: String,
    val email: String,
    val sex: Sex,
    val title: String = "",
    val descriptionShortSource: String = "",
    val descriptionShortCompiled: String = "",
    val descriptionLongSource: String = "",
    val descriptionLongCompiled: String = "",
    override val id: ULong? = null
) : ULongIdModel {

    fun withId(id: ULong?): UserModel =
        copy(id = id)

    companion object {
        val NULL_USER: UserModel = UserModel("", "", "", "", Sex.NON_BINARY)

        fun fromPrincipal(it: Principal): UserModel =
            UserModel(
                it.username,
                it.password,
                it.nick,
                it.email,
                it.sex,
                it.title,
                it.descriptionShortSource,
                it.descriptionShortCompiled,
                it.descriptionLongSource,
                it.descriptionLongCompiled,
                it.id
            )
    }
}