package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.data.UserSession
import darkladyblog.darkladyblog.server.base.Controller
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

class OAuthController : Controller("/oauth") {

    override fun Route.routes() {
        environment.config.config("oauth").let { configs ->
            configs.toMap().keys.forEach { configKey: String ->
                runCatching { configs.config(configKey) }.onSuccess { config ->
                    authenticate(configKey) {
                        get("/signin") {
                            call.respondRedirect("/callback?from=$configKey")
                        }
                        get("/callback") {
                            val from = call.parameters["from"]
                            val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                            call.sessions.set(UserSession(null, principal?.accessToken.toString()))
                            call.respondRedirect("/")
                        }
                    }
                }
            }

        }
    }
}