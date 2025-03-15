package darkladyblog.darkladyblog.common.model

import dev.fritz2.core.Lenses
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class AssistantMessage(val type: AssistantMessageType, val content: String) {
    companion object
}

enum class AssistantMessageType {
    QUESTION,
    ANSWER
}