package darkladyblog.darkladyblog.client.intercept

import darkladyblog.darkladyblog.client.services.Alerts
import darkladyblog.darkladyblog.common.config.DEBUG
import darkladyblog.darkladyblog.common.config.DEBUG_REQUESTS
import darkladyblog.darkladyblog.common.log.log
import dev.fritz2.remote.Middleware
import dev.fritz2.remote.Request
import dev.fritz2.remote.Response

class ToastMiddleware(
    private val onSuccess: Response.() -> Unit = {},
    private val onFailure: Response.() -> Unit = {
        Alerts.error(statusText, "HTTP $status")
    },
) : Middleware {
    override suspend fun enrichRequest(request: Request): Request {
        if (DEBUG && DEBUG_REQUESTS)
            log.info("doing request @${request.hashCode()} ${request.method} ${request.url}: ", request)
        return request
    }

    override suspend fun handleResponse(response: Response): Response {
        if (DEBUG && DEBUG_REQUESTS)
            log.info(
                "getting response @${response.request.hashCode()} from ${response.request.method} ${response.request.url} [${response.status} ${response.statusText}]: ",
                response
            )
        if (response.ok) onSuccess(response) else onFailure(response)
        response.stopPropagation()
        return response
    }
}