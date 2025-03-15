package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.server.base.RestController
import darkladyblog.darkladyblog.server.db.Topics
import darkladyblog.darkladyblog.server.repositories.TopicRepository
import darkladyblog.darkladyblog.server.services.app.TopicRepositoryService
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.builtins.serializer
import org.koin.core.annotation.Single

@Single
class TopicRestController(topicRepositoryService: TopicRepositoryService) :
    RestController<Topics, ULong, TopicModel, TopicRepository, TopicRepositoryService>(
        "/topics", topicRepositoryService, ULong.serializer(), TopicModel.serializer()
    ) {

    override fun Route.additionalRoutes() {
        get("/blog/{id}") {
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
        get("/blog/{id}/count") {
            call.respond(repositoryService.count(idParam(call.parameters)))
        }
    }
}
