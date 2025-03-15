package darkladyblog.darkladyblog.server.base

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import io.ktor.utils.io.core.Closeable
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Logger

abstract class Controller(val path: String) : KoinComponent, Closeable {

    @OptIn(KoinInternalApi::class)
    protected val log: Logger
        get() = getKoin().logger

    fun init(routeContext: Route) {
        routeContext.route("/$path") {
            routes()
        }
    }

    protected open fun Route.routes() {}

    open override fun close() {
        log.info("closing $this")
    }

    override fun toString(): String =
        "${this::class.qualifiedName}->$path"

}
