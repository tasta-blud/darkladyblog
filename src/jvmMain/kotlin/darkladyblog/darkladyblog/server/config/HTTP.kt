package darkladyblog.darkladyblog.server.config

import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.CachingOptions
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.conditionalheaders.ConditionalHeaders
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.plugins.partialcontent.PartialContent
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import io.ktor.server.webjars.Webjars

fun Application.configureHTTP() {
    install(ContentNegotiation) {
        json()
    }
    install(PartialContent) {
        maxRangeCount = 10
    }
    install(ConditionalHeaders)
    install(Compression)
    install(CachingHeaders) {
        options { call, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                else -> null
            }
        }
    }

    install(Webjars) {
        path = "/webjars"
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            this@configureHTTP.log.error("Uncaught exception", cause)
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    install(RequestValidation) {
        validate<String> { bodyText ->
            if (bodyText.isBlank())
                ValidationResult.Invalid("Body text not present")
            else ValidationResult.Valid
        }
    }
    install(DoubleReceive)
    install(AutoHeadResponse)
    routing {
        staticResources("/", "static")
    }
    install(Resources)
}
