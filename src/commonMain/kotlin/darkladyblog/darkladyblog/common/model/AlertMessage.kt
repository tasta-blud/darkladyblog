package darkladyblog.darkladyblog.common.model

import dev.fritz2.core.Lenses
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class AlertMessage(
    val message: String,
    val header: String? = null,
    val type: AlertType = AlertType.INFO,
) {
    companion object
}

enum class AlertType {
    ERROR,
    WARNING,
    INFO,
    SUCCESS
}