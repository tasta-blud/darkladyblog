package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.common.config.Config.WS_ALERTS
import darkladyblog.darkladyblog.common.config.Config.WS_ASSISTANT
import darkladyblog.darkladyblog.server.services.AlertMessageSender
import darkladyblog.darkladyblog.server.services.assistant.AssistantService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.seconds


fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket(WS_ALERTS) {
            val alertMessageSender: AlertMessageSender by inject()
            alertMessageSender(this)
        }
        webSocket(WS_ASSISTANT) {
            val assistantService: AssistantService by inject()
            assistantService(this)
        }
    }
}
