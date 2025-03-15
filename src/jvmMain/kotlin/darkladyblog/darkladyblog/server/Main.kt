package darkladyblog.darkladyblog.server

import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    args.indexOf("development").let { index ->
        if (index != -1 && args.size > index + 1) System.setProperty("io.ktor.development", args[index + 1])
    }
    EngineMain.main(args)
}
