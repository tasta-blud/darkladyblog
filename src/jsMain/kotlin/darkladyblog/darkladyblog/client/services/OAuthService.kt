package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.AbstractHttpService
import darkladyblog.darkladyblog.client.util.method
import darkladyblog.darkladyblog.common.config.OAuthClient
import io.ktor.http.HttpMethod

object OAuthService : AbstractHttpService("/auth") {

    suspend fun signIn(oAuth: OAuthClient): Unit? =
        http.useToastHttp()
            .request<Unit> { method(HttpMethod.Get).append(oAuth.url) }
            .onSuccess {
//                if (it == null) auth.clear()
//                else auth.complete(Credentials(it.username, it.password))
            }
            .getOrNull()
}