package darkladyblog.darkladyblog.client.util

import dev.fritz2.remote.Request
import io.ktor.http.HttpMethod

fun Request.method(method: String): Request =
    copy(method = method)

fun Request.method(method: HttpMethod): Request =
    copy(method = method.value)