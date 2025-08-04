package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.controllers.ICommentRestController
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.server.base.RestController
import darkladyblog.darkladyblog.server.db.Comments
import darkladyblog.darkladyblog.server.repositories.CommentRepository
import darkladyblog.darkladyblog.server.services.app.CommentRepositoryService
import io.ktor.server.application.ApplicationCall
import org.koin.core.annotation.Single

@Single
class CommentRestController(commentRepositoryService: CommentRepositoryService, call: ApplicationCall) :
    RestController<Comments, ULong, CommentModel, CommentRepository, CommentRepositoryService>(
        commentRepositoryService,
        call
    ), ICommentRestController {

    override suspend fun allByTopic(
        topicId: ULong,
        offset: Long?,
        limit: Int?,
        order: Array<Sorting>
    ): List<CommentModel> =
        repositoryService.all(topicId, offset, limit, *mapOrder(order))

    override suspend fun allByTopicAndParent(
        topicId: ULong,
        commentId: ULong,
        offset: Long?,
        limit: Int?,
        order: Array<Sorting>
    ): List<CommentModel> =
        repositoryService.all(topicId, commentId, offset, limit, *mapOrder(order))

    override suspend fun countByTopic(topicId: ULong): Long =
        repositoryService.count(topicId)

    override suspend fun countByTopicAndParent(topicId: ULong, commentId: ULong): Long =
        repositoryService.count(topicId, commentId)
}
