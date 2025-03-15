package darkladyblog.darkladyblog.common.config

import darkladyblog.darkladyblog.common.util.dataType

object Config {

    const val DEBUG: Boolean = true
    const val DEBUG_REQUESTS: Boolean = true

    const val PAGE_SIZE: Int = 10

    const val ENDPOINT: String = "/api"
    const val REAL_SERVER_HOST: String = "localhost:8080"
    const val REAL_SERVER_URL: String = "http://$REAL_SERVER_HOST$ENDPOINT"
    const val REAL_CLIENT_HOST: String = "localhost:3000"
    const val REAL_CLIENT_URL: String = "http://$REAL_CLIENT_HOST"

    val SERVER_HOST: String = if (dataType == "client") "$REAL_CLIENT_HOST$ENDPOINT" else "$REAL_SERVER_HOST$ENDPOINT"
    val SERVER_URL: String = "http://$SERVER_HOST"
    val CLIENT_HOST: String = if (dataType == "client") REAL_CLIENT_HOST else REAL_SERVER_HOST
    val CLIENT_URL: String = "http://$CLIENT_HOST"

    const val WS_ALERTS: String = "$ENDPOINT/alerts"
    const val WS_SERVER_ALERTS: String = "ws://$REAL_SERVER_HOST$WS_ALERTS"

    const val WS_ASSISTANT: String = "$ENDPOINT/assistant"
    const val WS_SERVER_ASSISTANT: String = "ws://$REAL_SERVER_HOST$WS_ASSISTANT"
}
