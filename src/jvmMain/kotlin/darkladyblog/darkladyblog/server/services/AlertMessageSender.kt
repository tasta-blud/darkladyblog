package darkladyblog.darkladyblog.server.services

import darkladyblog.darkladyblog.common.model.AlertMessage
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.utils.io.core.Closeable
import io.ktor.websocket.Frame
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class AlertMessageSender : KoinComponent, Closeable {

    private val messageResponseFlow: MutableSharedFlow<AlertMessage> = MutableSharedFlow()
    val sharedFlow: SharedFlow<AlertMessage> = messageResponseFlow.asSharedFlow()

    suspend operator fun invoke(session: DefaultWebSocketServerSession): DefaultWebSocketServerSession =
        session.apply {
            launch {
                sharedFlow.collect {
                    send(session, it)
                }
            }
            for (frame in incoming) {
                frame
            }
        }

    suspend fun sendAll(alertMessage: AlertMessage) {
        try {
            messageResponseFlow.emit(alertMessage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun send(session: DefaultWebSocketServerSession, alertMessage: AlertMessage) {
        session.outgoing.send(Frame.Text(Json.encodeToString<AlertMessage>(alertMessage)))
    }

    override fun close() {
        MainScope().launch(Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
        }
    }
}