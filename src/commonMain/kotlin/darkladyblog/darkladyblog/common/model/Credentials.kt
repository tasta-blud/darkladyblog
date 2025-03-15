package darkladyblog.darkladyblog.common.model

import dev.fritz2.core.Lenses
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class Credentials(
    val username: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
) {
    companion object
}
