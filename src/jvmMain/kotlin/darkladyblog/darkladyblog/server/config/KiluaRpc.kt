package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.server.AppModule
import dev.kilua.rpc.applyRoutes
import dev.kilua.rpc.getAllServiceManagers
import dev.kilua.rpc.initRpc
import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.routing.routing
import org.koin.core.annotation.KoinInternalApi
import org.koin.ksp.generated.module

@OptIn(KoinInternalApi::class)
fun Application.configureKiluaRpc() {
    initRpc(false, AppModule().module)
    routing {
        getAllServiceManagers().forEach {
            applyRoutes(it)
            log.info("processed $it")
        }
    }
}