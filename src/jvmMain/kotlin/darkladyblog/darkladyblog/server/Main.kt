package darkladyblog.darkladyblog.server

import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    val ioKtorDevelopment = "io.ktor.development"
    val isDevelopment =
        System.getProperty(ioKtorDevelopment).toBoolean() ||
                args.getOrNull(args.indexOf("development") + 1).toBoolean().also {
                    if (it) System.setProperty(ioKtorDevelopment, true.toString())
                }
    EngineMain.main(args + if (isDevelopment) "application-development.yaml" else "application.yaml")
}
