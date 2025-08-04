package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.controllers.ITopicRestController
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.server.base.RestController
import darkladyblog.darkladyblog.server.db.Topics
import darkladyblog.darkladyblog.server.repositories.TopicRepository
import darkladyblog.darkladyblog.server.services.app.TopicRepositoryService
import io.ktor.server.application.ApplicationCall
import org.koin.core.annotation.Single

@Single
class TopicRestController(topicRepositoryService: TopicRepositoryService, call: ApplicationCall) :
    RestController<Topics, ULong, TopicModel, TopicRepository, TopicRepositoryService>(topicRepositoryService, call),
    ITopicRestController {
    override suspend fun exists(id: ULong): Boolean =
        repositoryService.exists(id)

    override suspend fun allByBlog(
        blogId: ULong,
        offset: Long?,
        limit: Int?,
        order: Array<Sorting>
    ): List<TopicModel> =
        repositoryService.all(blogId, offset, limit, *mapOrder(order))

    override suspend fun countByBlog(blogId: ULong): Long =
        repositoryService.count(blogId)

}
