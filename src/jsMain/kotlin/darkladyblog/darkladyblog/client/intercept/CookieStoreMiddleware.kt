package darkladyblog.darkladyblog.client.intercept

import dev.fritz2.remote.Middleware
import dev.fritz2.remote.Request
import dev.fritz2.remote.Response
import io.ktor.http.Cookie
import io.ktor.http.HttpHeaders
import io.ktor.http.parseServerSetCookieHeader
import io.ktor.http.renderSetCookieHeader

class CookieStoreMiddleware : Middleware {
    val cookies: MutableSet<Cookie> = mutableSetOf()

    fun clear(): Unit =
        cookies.clear()

    override suspend fun enrichRequest(request: Request): Request {
        cookies.forEach {
            request.header(HttpHeaders.Cookie, renderSetCookieHeader(it))
        }
        return request
    }

    override suspend fun handleResponse(response: Response): Response =
        response.apply {
            headers.get(HttpHeaders.SetCookie)?.also {
                cookies += parseServerSetCookieHeader(it)
            }
        }

}