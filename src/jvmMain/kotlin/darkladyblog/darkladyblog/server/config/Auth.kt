@file:Suppress("D")

package darkladyblog.darkladyblog.server.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import darkladyblog.darkladyblog.common.config.Config.SERVER_URL
import darkladyblog.darkladyblog.server.controllers.AuthController
import darkladyblog.darkladyblog.server.data.UserSession
import darkladyblog.darkladyblog.server.services.UserRepositoryService
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authentication
import io.ktor.server.auth.form
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.oauth
import io.ktor.server.auth.session
import io.ktor.server.response.respond
import org.koin.ktor.ext.get


fun Application.configureAuth() {
    authentication {
        form(AuthController.AUTH_FORM) {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                val authService: UserRepositoryService = get<UserRepositoryService>()
                if (authService.getByUsername(credentials.name, credentials.password) == null) {
                    null
                } else {
                    UserIdPrincipal(credentials.name)
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Credentials are not valid")
            }
        }
        session<UserSession>(AuthController.AUTH_SESSION) {
            validate { session ->
                val authService: UserRepositoryService = get<UserRepositoryService>()
                if (session.user == null) {
                    null
                } else if (authService.getByUsername(session.user.username) == null) {
                    null
                } else {
                    session
                }
            }
            challenge {
            }
        }
        val applicationConfig = this@configureAuth.environment.config
        val jwtAudience = applicationConfig.property("jwt.audience").getString()
        val jwtDomain = applicationConfig.property("jwt.domain").getString()
        val jwtRealm = applicationConfig.property("jwt.realm").getString()
        val jwtSecret = applicationConfig.property("jwt.secret").getString()
        jwt(AuthController.AUTH_JWT) {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    if (credential.payload.getClaim("username").asString() != "") {
                        JWTPrincipal(credential.payload)
                    } else null
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
        applicationConfig.config("oauth").let { configs ->
            configs.toMap().keys.forEach { configKey ->
                runCatching { configs.config(configKey) }.onSuccess { config ->
                    oauth(configKey) {
                        urlProvider = { "$SERVER_URL/auth/callback?from=$configKey" }
                        providerLookup = {
                            OAuthServerSettings.OAuth2ServerSettings(
                                name = configKey,
                                authorizeUrl = config.propertyOrNull("authorizeUrl")?.getString()
                                    ?: configs.property("authorizeUrl").getString(),
                                accessTokenUrl = config.propertyOrNull("accessTokenUrl")?.getString()
                                    ?: configs.property("accessTokenUrl").getString(),
                                requestMethod = HttpMethod.Post,
                                clientId = config.propertyOrNull("clientId")?.getString()
                                    ?: configs.property("clientId")
                                        .getString(),
                                clientSecret = config.propertyOrNull("clientSecret")?.getString()
                                    ?: configs.property("clientSecret").getString(),
                                defaultScopes = config.propertyOrNull("defaultScopes")?.getList()
                                    ?: configs.propertyOrNull(
                                        "defaultScopes"
                                    )?.getList() ?: listOf(),
                            )
                        }
                        client = HttpClient(Apache)
                    }
                }
            }
        }
    }

}
