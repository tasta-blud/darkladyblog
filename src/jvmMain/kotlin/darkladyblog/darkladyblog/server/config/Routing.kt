package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.config.ENDPOINT
import darkladyblog.darkladyblog.server.base.Controller
import io.ktor.server.application.Application
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.core.annotation.KoinInternalApi
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.KoinApplicationStopPreparing


@OptIn(KoinInternalApi::class)
fun Application.configureRouting() {
    val koin = getKoin()
    routing {
        route(ENDPOINT) {
            val restControllers = koin.getAll<IRestController<*, *>>()
            restControllers.forEach {
                environment.log.info("registered RestController $it")
            }
            val controllers = koin.getAll<Controller>()
            monitor.subscribe(KoinApplicationStopPreparing) {
                controllers.forEach { it.close() }
            }
            val controllersRemaining = controllers - restControllers
            controllersRemaining.forEach {
                environment.log.info("registered Controller $it")
            }
        }
    }
}
