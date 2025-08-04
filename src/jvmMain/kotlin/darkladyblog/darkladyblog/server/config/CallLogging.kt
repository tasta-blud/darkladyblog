package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.common.config.DEBUG_REQUESTS
import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.calllogging.processingTimeMillis
import io.ktor.server.plugins.origin
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path

fun Application.configureCallLogging() {
    install(CallId) {
        header(HttpHeaders.XRequestId)
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
    install(CallLogging) {
        callIdMdc("call-id")
        @Suppress("KotlinConstantConditions")
        if (DEBUG_REQUESTS)
            format { call ->
                val status = call.response.status()
                val httpMethod = call.request.httpMethod.value
                val userAgent = call.request.headers["User-Agent"]
                val path = call.request.path()
                val queryParams =
                    call.request.queryParameters
                        .entries()
                        .joinToString(", ") { "${it.key}=${it.value}" }
                val duration = call.processingTimeMillis()
                val remoteHost = call.request.origin.remoteHost
                val coloredStatus =
                    when {
                        status == null -> "\u001B[33mUNKNOWN\u001B[0m"
                        status.value < 300 -> "\u001B[32m$status\u001B[0m"
                        status.value < 400 -> "\u001B[33m$status\u001B[0m"
                        else -> "\u001B[31m$status\u001B[0m"
                    }
                val coloredMethod = "\u001B[36m$httpMethod\u001B[0m"
                """
        |
        |------------------------ Request Details ------------------------
        |Status: $coloredStatus
        |Method: $coloredMethod
        |Path: $path
        |Query Params: $queryParams
        |Remote Host: $remoteHost
        |User Agent: $userAgent
        |Duration: ${duration}ms
        |------------------------------------------------------------------
        |
  """.trimMargin()
            }
    }
}
