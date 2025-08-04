package darkladyblog.darkladyblog.client.base.rest

import darkladyblog.darkladyblog.client.intercept.AuthenticationMiddleware
import darkladyblog.darkladyblog.client.intercept.CookieStoreMiddleware
import darkladyblog.darkladyblog.client.intercept.ToastMiddleware
import darkladyblog.darkladyblog.client.services.Alerts
import darkladyblog.darkladyblog.common.config.SERVER_URL
import dev.fritz2.remote.Request
import dev.fritz2.remote.Response
import dev.fritz2.remote.decoded
import dev.fritz2.remote.http
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.SAME_ORIGIN

abstract class AbstractHttpService(val path: String, val useCookies: Boolean = true, val useAuth: Boolean = true) {
    val cookieStore: CookieStoreMiddleware by lazy { CookieStoreMiddleware() }
    val auth: AuthenticationMiddleware by lazy { AuthenticationMiddleware() }

    val http: Request
        get() = http(SERVER_URL).append(path)
            .let { if (useCookies) it.useCookies() else it }
            .let { if (useAuth) it.useAuth() else it }
            .credentials(RequestCredentials.SAME_ORIGIN)

    fun clearCookies(): Unit =
        cookieStore.clear()

    fun Request.useToast(): Request =
        use(ToastMiddleware())

    fun Request.useToast(onSuccess: Response.() -> Unit, onFailure: Response.() -> Unit): Request =
        use(ToastMiddleware(onSuccess, onFailure))

    fun Request.useToastHttp(): Request =
        use(
            ToastMiddleware(
                onSuccess = {},
                onFailure = { Alerts.error(statusText, "HTTP $status") }
            ))

    fun Request.useToastEmpty(): Request =
        use(ToastMiddleware({}, {}))

    fun Request.useCookies(): Request =
        use(cookieStore)

    fun Request.useAuth(): Request =
        use(auth)

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun Request.requestRaw(filter: Request.() -> Request): Result<Response> =
        runCatching { filter().execute() }
            .onFailure { Alerts.error(it) }

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun Request.requestStatus(filter: Request.() -> Request): Result<Boolean> =
        runCatching { filter().execute() }
            .mapCatching { (it.ok) }
            .onFailure { Alerts.error(it) }

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun <reified T2> Request.requestOf(
        filter: Request.() -> Request
    ): Result<T2?> =
        runCatching { filter().execute() }
            .mapCatching { if (it.ok) it.decoded<T2>() else null }
            .onFailure { Alerts.error(it) }

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun <T2> Request.requestOf(
        serializer: KSerializer<T2>,
        filter: Request.() -> Request
    ): Result<T2?> =
        runCatching { filter().execute() }
            .mapCatching { if (it.ok) it.decoded(serializer) else null }
            .onFailure { Alerts.error(it) }

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun <T2> Request.requestOf(
        filter: Request.() -> Request,
        resp: Response.() -> T2
    ): Result<T2?> =
        runCatching { filter().execute() }
            .mapCatching { if (it.ok) it.resp() else null }
            .onFailure { Alerts.error(it) }

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun <reified T> Request.request(
        filter: Request.() -> Request
    ): Result<T?> =
        requestOf<T> { filter() }

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun <T> Request.request(
        serializer: KSerializer<T>,
        filter: Request.() -> Request
    ): Result<T?> =
        requestOf(serializer) { filter() }

    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun <T> Request.request(
        filter: Request.() -> Request,
        resp: Response.() -> T
    ): Result<T?> =
        requestOf({ filter() }) { resp() }
}