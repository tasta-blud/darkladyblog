package darkladyblog.darkladyblog.server.util

import darkladyblog.darkladyblog.common.util.buildCss
import io.ktor.http.ContentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondText
import kotlinx.css.CssBuilder

suspend inline fun ApplicationCall.respondCss(noinline builder: CssBuilder.() -> Unit): Unit =
    respondText(buildCss(builder), ContentType.Text.CSS)
