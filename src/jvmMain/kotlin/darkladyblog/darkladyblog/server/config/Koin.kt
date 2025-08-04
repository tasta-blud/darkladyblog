package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.server.AppModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.pluginOrNull
import io.ktor.utils.io.core.Closeable
import org.koin.core.annotation.KoinInternalApi
import org.koin.ksp.generated.module
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    pluginOrNull(Koin) ?: install(Koin) {
        slf4jLogger()
        modules(AppModule().module)
    }
    getKoin().declare(this@configureKoin)

    monitor.subscribe(KoinApplicationStarted) {
        environment.log.info("Koin started.")
    }
    monitor.subscribe(KoinApplicationStopPreparing) {
        environment.log.info("Koin stopping...")
        @OptIn(KoinInternalApi::class)
        getKoin().getAll<Closeable>().forEach { it.close() }
    }
    monitor.subscribe(KoinApplicationStopped) {
        environment.log.info("Koin stopped.")
    }
}
