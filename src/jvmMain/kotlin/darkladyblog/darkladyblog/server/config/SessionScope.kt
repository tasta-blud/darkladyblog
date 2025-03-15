package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.server.plugins.sessionscope.KoinSessionScope
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureSessionScope() {
    install(KoinSessionScope)
}