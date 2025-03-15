package darkladyblog.darkladyblog.server.services.assistant

import darkladyblog.darkladyblog.common.model.AssistantMessage
import darkladyblog.darkladyblog.common.model.AssistantMessageType
import darkladyblog.darkladyblog.i18n.AssistantTranslations
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class AssistantService(private val aiService: AssistantAIService) : KoinComponent {

    suspend operator fun invoke(session: DefaultWebSocketServerSession): DefaultWebSocketServerSession =
        session.apply {
            send(
                Frame.Text(
                    AssistantMessage(
                        AssistantMessageType.ANSWER,
                        AssistantTranslations.assistant_message_hello()
                    ).content
                )
            )
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val received = AssistantMessage(AssistantMessageType.QUESTION, frame.readText())
                    if (aiService.isShutdown(received.content)) {
                        send(
                            Frame.Text(
                                AssistantMessage(
                                    AssistantMessageType.ANSWER,
                                    AssistantTranslations.assistant_message_bye()
                                ).content
                            )
                        )
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said bye"))
                    } else {
                        send(
                            Frame.Text(
                                AssistantMessage(
                                    AssistantMessageType.ANSWER,
                                    aiService.process(received.content)
                                ).content
                            )
                        )
                    }
                }
            }
        }
}