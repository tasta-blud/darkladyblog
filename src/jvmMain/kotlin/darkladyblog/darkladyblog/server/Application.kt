package darkladyblog.darkladyblog.server

import darkladyblog.darkladyblog.server.config.configureAuth
import darkladyblog.darkladyblog.server.config.configureCallLogging
import darkladyblog.darkladyblog.server.config.configureHTTP
import darkladyblog.darkladyblog.server.config.configureJobs
import darkladyblog.darkladyblog.server.config.configureKiluaRpc
import darkladyblog.darkladyblog.server.config.configureKoin
import darkladyblog.darkladyblog.server.config.configureResourceRouting
import darkladyblog.darkladyblog.server.config.configureRouting
import darkladyblog.darkladyblog.server.config.configureSecurity
import darkladyblog.darkladyblog.server.config.configureSessionScope
import darkladyblog.darkladyblog.server.config.configureSessions
import darkladyblog.darkladyblog.server.config.configureSockets
import darkladyblog.darkladyblog.server.config.testData
import io.ktor.server.application.Application

fun Application.module() {
    configureSockets()
    configureKiluaRpc()
    configureKoin()
    configureSessions()
    configureSessionScope()
    configureHTTP()
    configureCallLogging()
    configureSecurity()
    configureAuth()
    configureRouting()
    configureResourceRouting()
    configureJobs()
    testData()
}
