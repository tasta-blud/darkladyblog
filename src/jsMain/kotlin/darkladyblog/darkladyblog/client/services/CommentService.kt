package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.client.intercept.runWithToastAsync
import darkladyblog.darkladyblog.common.controllers.ICommentRestController
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.CommentModel
import dev.kilua.rpc.getService

object CommentService :
    RestService<ULong, CommentModel, ICommentRestController>(controller = getService<ICommentRestController>()) {

    suspend fun all(
        topicId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        order: Array<Sorting> = arrayOf(Sorting(ColumnName("id"), SortDirection.ASC)),
    ): List<CommentModel>? =
        runWithToastAsync { controller.allByTopic(topicId, offset, limit, order) }.getOrNull()

    suspend fun all(
        topicId: ULong,
        commentId: ULong? = null,
        offset: Long? = null,
        limit: Int? = null,
        order: Array<Sorting> = arrayOf(Sorting(ColumnName("id"), SortDirection.ASC)),
    ): List<CommentModel>? =
        runWithToastAsync { controller.allByTopicAndParent(topicId, commentId!!, offset, limit, order) }.getOrNull()

    suspend fun count(topicId: ULong): Long? =
        runWithToastAsync { controller.countByTopic(topicId) }.getOrNull()

    suspend fun count(topicId: ULong, commentId: ULong? = null): Long? =
        runWithToastAsync { controller.countByTopicAndParent(topicId, commentId!!) }.getOrNull()

}