package darkladyblog.darkladyblog.common.config

import kotlinx.browser.document
import org.w3c.dom.get

actual val DEBUG: Boolean by lazy { true }
actual val DEBUG_REQUESTS: Boolean by lazy { true }

actual val PAGE_SIZE: Int by lazy { 10 }

actual val ENDPOINT: String by lazy { "/api" }

actual val REAL_SERVER_HOST: String by lazy { "localhost:8080" }
actual val REAL_SERVER_URL: String by lazy { "http://$REAL_SERVER_HOST$ENDPOINT" }
actual val REAL_CLIENT_HOST: String by lazy { "localhost:3000" }
actual val REAL_CLIENT_URL: String by lazy { "http://$REAL_CLIENT_HOST" }
actual val SERVER_HOST: String by lazy {
    when (dataType) {
        DataType.SERVED -> "$locationServed$ENDPOINT"
        DataType.STANDALONE -> "$REAL_CLIENT_HOST$ENDPOINT"
    }
}
actual val SERVER_URL: String by lazy { "http://$SERVER_HOST" }
actual val CLIENT_HOST: String by lazy {
    when (dataType) {
        DataType.SERVED -> locationServed
        DataType.STANDALONE -> REAL_CLIENT_HOST
    }
}
actual val CLIENT_URL: String by lazy { "http://$CLIENT_HOST" }

actual val WS_ALERTS: String by lazy { "$ENDPOINT/alerts" }
actual val WS_SERVER_ALERTS: String by lazy { "ws://$REAL_SERVER_HOST$WS_ALERTS" }
actual val WS_ASSISTANT: String by lazy { "$ENDPOINT/assistant" }
actual val WS_SERVER_ASSISTANT: String by lazy { "ws://$REAL_SERVER_HOST$WS_ASSISTANT" }

private enum class DataType {
    STANDALONE, SERVED
}

private val dataType: DataType by lazy { DataType.valueOf(document.body!!.dataset["type"]!!.uppercase()) }
private val locationServed: String by lazy { (document.location!!.host).removePrefix(document.location!!.protocol + "//") }