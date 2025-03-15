package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.model.Principal
import darkladyblog.darkladyblog.server.base.Controller
import darkladyblog.darkladyblog.server.data.UserSession
import darkladyblog.darkladyblog.server.services.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import org.koin.core.annotation.Single
import org.koin.ktor.plugin.scope

@Single
class AuthController() : Controller(ROUTE) {

    companion object {
        const val ROUTE: String = "/auth"
        const val AUTH_FORM: String = "form"
        const val AUTH_SESSION: String = "session"
        const val AUTH_JWT: String = "jwt"
        const val AUTH_OAUTH_GOOGLE: String = "oauth.google"
    }

    override fun Route.routes() {
        authenticate(AUTH_FORM) {
            post("/login") {
                val authService: AuthService = call.scope.get<AuthService>()
                call.principal<UserIdPrincipal>()?.let { authService.login(it.name) }
                call.respond(authService.getUser()?.user!!.let {
                    Principal(
                        it.username,
                        it.password,
                        it.nick,
                        it.email,
                        it.sex,
                        it.title,
                        it.descriptionShortSource,
                        it.descriptionShortCompiled,
                        it.descriptionLongSource,
                        it.descriptionLongCompiled,
                        it.id,
                    )
                })
            }
        }
        authenticate(AUTH_SESSION) {
            post("/logout") {
                val authService: AuthService = call.scope.get<AuthService>()
                authService.logout()
                call.respond(Principal.NULL_PRINCIPAL)
            }
            get("/me") {
                val authService: AuthService = call.scope.get<AuthService>()
                val user = authService.getUser()
                if (user == null) call.respond(HttpStatusCode.Forbidden)
                else call.respond(user.user!!.let {
                    Principal(
                        it.username,
                        it.password,
                        it.nick,
                        it.email,
                        it.sex,
                        it.title,
                        it.descriptionShortSource,
                        it.descriptionShortCompiled,
                        it.descriptionLongSource,
                        it.descriptionLongCompiled,
                        it.id,
                    )
                })
            }
        }
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
