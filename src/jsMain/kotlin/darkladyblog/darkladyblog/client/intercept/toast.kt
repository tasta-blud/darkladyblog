package darkladyblog.darkladyblog.client.intercept

import darkladyblog.darkladyblog.client.services.Alerts
import darkladyblog.darkladyblog.common.log.LoggerFactory
import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.ServiceException
import kotlinx.browser.window

inline fun <R> runWithToast(block: () -> R): Result<R> =
    runCatching { block() }.onSuccess { if (it == null) handleNull() }.onFailure { handleError(it) }

suspend inline fun <R> runWithToastAsync(block: suspend () -> R): Result<R> =
    runCatching { block() }.onSuccess { if (it == null) handleNull() }.onFailure { handleError(it) }

private var lastError: Throwable? = null
fun handleError(it: Throwable) {
    LoggerFactory.getLogger(it::class.simpleName ?: "")
        .error("RPC ERROR", it::class.simpleName, "|", it.message, "|", it.cause)
    when {
        it == lastError -> {}
        it.message == lastError?.message -> {}
        it is ServiceException -> Alerts.error(it.message ?: "Error", it::class.simpleName)
        it is AbstractServiceException -> Alerts.error(it.message ?: "Error", it::class.simpleName)
        else -> Alerts.error(it.message ?: "Error", it::class.simpleName)
    }
    lastError = it
    window.setTimeout({ lastError = null }, 1000)
}

fun handleNull() {
    LoggerFactory.getLogger("").error("Empty response")
}
