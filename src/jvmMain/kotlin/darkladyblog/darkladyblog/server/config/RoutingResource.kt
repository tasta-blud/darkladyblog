package darkladyblog.darkladyblog.server.config

import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

@Serializable
@Resource("/users")
class UsersRouting(val sort: String? = "new")

fun Application.configureResourceRouting() {
    routing {
        get<UsersRouting> { article ->
            call.respond("List of articles sorted starting from ${article.sort}")
        }
    }
}
