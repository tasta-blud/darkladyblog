package darkladyblog.darkladyblog.server.services.assistant

import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class AssistantAIService : KoinComponent {

    fun isShutdown(message: String): Boolean =
        message == "bye"

    fun process(received: String): String =
        "Hi, $received!"

}