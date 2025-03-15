package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.AbstractHttpService
import darkladyblog.darkladyblog.client.util.method
import darkladyblog.darkladyblog.common.model.Credentials
import darkladyblog.darkladyblog.common.model.Principal
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import org.w3c.dom.url.URLSearchParams

object LoginService : AbstractHttpService("/auth") {

    suspend fun doLogin(user: Credentials): Principal? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.FormUrlEncoded.toString())
            .body(URLSearchParams().apply {
                append("username", user.username)
                append("password", user.password)
            }.toString())
            .request<Principal> { method(HttpMethod.Post).append("/login") }
            .onSuccess {
                if (it == null) auth.clear()
                else auth.complete(Credentials(it.username, it.password))
            }
            .getOrNull()

    suspend fun doLogout() {
        http.useToastEmpty()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request<Principal> { method(HttpMethod.Post).append("/logout") }
            .onSuccess { auth.clear() }
    }

    suspend fun doMe(): Principal? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request<Principal> { method(HttpMethod.Get).append("/me") }
            .getOrNull()

    suspend fun tryLogin(): Principal? =
        http.useToastEmpty()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request<Principal> { method(HttpMethod.Get).append("/me") }
            .mapCatching { it!! }
            .onSuccess { auth.complete(Credentials(it.username, it.password)) }
            .onFailure { auth.clear() }
            .getOrNull()
}