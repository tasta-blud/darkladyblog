package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.server.base.RestController
import darkladyblog.darkladyblog.server.db.Comments
import darkladyblog.darkladyblog.server.repositories.CommentRepository
import darkladyblog.darkladyblog.server.services.app.CommentRepositoryService
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.builtins.serializer
import org.koin.core.annotation.Single

@Single
class CommentRestController(commentRepositoryService: CommentRepositoryService) :
    RestController<Comments, ULong, CommentModel, CommentRepository, CommentRepositoryService>(
        "/comments",
        commentRepositoryService,
        ULong.serializer(),
        CommentModel.serializer()
    ) {

    override fun Route.additionalRoutes() {
        get("/topic/{id}") {
            sendBodyList(
                call,
                repositoryService.all(
                    idParam(call.parameters),
                    offset = offsetParam(call),
                    limit = limitParam(call),
                    order = orderParam(call) ?: arrayOf(),
                )
            )
        }
        get("/topic/{id}/{pid}") {
            sendBodyList(
                call,
                repositoryService.all(
                    idParam(call.parameters),
                    idParam(call.parameters, "pid"),
                    offset = offsetParam(call),
                    limit = limitParam(call),
                    order = orderParam(call) ?: arrayOf(),
                )
            )
        }
        get("/topic/{id}/count") {
            call.respond(repositoryService.count(idParam(call.parameters)))
        }
        get("/topic/{id}/{pid}/count") {
            call.respond(repositoryService.count(idParam(call.parameters), idParam(call.parameters, "pid")))
        }
    }
}
