package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.client.base.store.RootStoreListBase
import darkladyblog.darkladyblog.common.config.Config
import darkladyblog.darkladyblog.common.model.AssistantMessage
import darkladyblog.darkladyblog.common.model.AssistantMessageType
import dev.fritz2.core.Handler
import dev.fritz2.core.Store
import dev.fritz2.remote.Session
import dev.fritz2.remote.Socket
import dev.fritz2.remote.body
import dev.fritz2.remote.websocket
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map

object AssistantMessageStore : RootStoreListBase<AssistantMessage>() {

    var callback: () -> Unit = {}

    val userInput: Store<String> = RootStoreBase("")

    private lateinit var session: Session

    private val socket: Socket = websocket(Config.WS_SERVER_ASSISTANT)

    operator fun invoke() {
        session = connect()
        var last: AssistantMessage? = null
        session.messages.body.map { content ->
            AssistantMessage(
                if (content == last?.content) AssistantMessageType.QUESTION else AssistantMessageType.ANSWER,
                content
            ).also { last = it }
        } handledBy add
        data.drop(1) handledBy { messages ->
            messages.lastOrNull()?.takeIf { it.content.isNotBlank() && it != last }?.let { session.send(it.content) }
                .also {
                    callback()
                }
        }
    }

    private fun connect(): Session =
        socket.connect().apply {
            state handledBy {
                if (it.asShort() == 1006.toShort()) connect()
            }
        }

    val onEnter: Handler<Unit> =
        handle {
            userInput.data.map { content -> AssistantMessage(AssistantMessageType.QUESTION, content) } handledBy add
            it
        }
}