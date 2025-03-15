package darkladyblog.darkladyblog.common.model

import dev.fritz2.core.Lenses
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Lenses
@Serializable
data class Principal(
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
    val id: ULong? = null
) {
    companion object {
        val NULL_PRINCIPAL: Principal = Principal("", "", "", "", Sex.NON_BINARY)
    }
}
