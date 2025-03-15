package darkladyblog.darkladyblog.common.model

import dev.fritz2.core.Lenses
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Lenses
@Serializable
data class Registration(
    val username: String,
    val password: String = "",
    @Transient
    val confirmPassword: String = password,
    val nick: String,
    val email: String,
    val sex: Sex,
    val title: String = "",
    val descriptionShortSource: String = "",
    val descriptionShortCompiled: String = "",
    val descriptionLongSource: String = "",
    val descriptionLongCompiled: String = "",
) {

    fun toUserModel(): UserModel = UserModel(
        username, password, nick, email, sex, title,
        descriptionShortSource, descriptionShortCompiled, descriptionLongSource, descriptionLongCompiled
    )

    companion object {
        val NULL_REGISTRATION: Registration = Registration("", "", "", "", "", Sex.NON_BINARY)
    }
}