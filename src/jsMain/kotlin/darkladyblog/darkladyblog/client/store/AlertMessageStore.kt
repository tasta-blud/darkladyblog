package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreListBase
import darkladyblog.darkladyblog.client.services.Alerts
import darkladyblog.darkladyblog.common.config.Config
import darkladyblog.darkladyblog.common.model.AlertMessage
import darkladyblog.darkladyblog.common.model.AlertType
import dev.fritz2.core.Handler
import dev.fritz2.remote.Session
import dev.fritz2.remote.Socket
import dev.fritz2.remote.body
import dev.fritz2.remote.websocket
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

object AlertMessageStore : RootStoreListBase<AlertMessage>() {

    lateinit var session: Session

    private val socket: Socket = websocket(Config.WS_SERVER_ALERTS)

    operator fun invoke() {
        session = connect()
        session.messages.body.map { string -> Json.Default.decodeFromString<AlertMessage>(string) } handledBy onMessage
    }

    private fun connect(): Session =
        socket.connect().apply {
            state handledBy {
                if (it.asShort() == 1006.toShort()) connect()
            }
        }

    private val onMessage: Handler<AlertMessage> = handle { currentList: List<AlertMessage>, message: AlertMessage ->
        when (message.type) {
            AlertType.ERROR -> Alerts.error(message.message, message.header)
            AlertType.WARNING -> Alerts.warning(message.message, message.header)
            AlertType.INFO -> Alerts.info(message.message, message.header)
            AlertType.SUCCESS -> Alerts.success(message.message, message.header)
        }
        currentList
    }
}