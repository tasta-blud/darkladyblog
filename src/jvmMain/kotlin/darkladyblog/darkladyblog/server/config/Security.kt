package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.common.config.Config.CLIENT_URL
import darkladyblog.darkladyblog.common.config.Config.REAL_CLIENT_HOST
import darkladyblog.darkladyblog.common.config.Config.REAL_CLIENT_URL
import darkladyblog.darkladyblog.common.config.Config.REAL_SERVER_HOST
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.csrf.CSRF
import io.ktor.server.plugins.defaultheaders.DefaultHeaders

fun Application.configureSecurity() {
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
        header(HttpHeaders.AccessControlAllowOrigin, REAL_CLIENT_URL)
        header(HttpHeaders.AccessControlAllowCredentials, "true")
        header(HttpHeaders.AccessControlAllowMethods, "HEAD, GET, POST")
        header(HttpHeaders.AccessControlAllowHeaders, "Content-Type, Authorization, Cookie, *")
    }
    install(CSRF) {
        allowOrigin(CLIENT_URL)
        allowOrigin(REAL_CLIENT_URL)
    }
    install(CORS) {
        allowMethod(HttpMethod.Head)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.Cookie)
        allowHost(REAL_SERVER_HOST)
        allowHost(REAL_CLIENT_HOST)
        allowCredentials = true
    }
}