package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.client.intercept.runWithToastAsync
import darkladyblog.darkladyblog.common.controllers.ITopicRestController
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.TopicModel
import dev.kilua.rpc.getService

object TopicService :
    RestService<ULong, TopicModel, ITopicRestController>(controller = getService<ITopicRestController>()) {

    suspend fun all(
        blogId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        order: Array<Sorting> = arrayOf(Sorting(ColumnName("id"), SortDirection.ASC)),
    ): List<TopicModel>? =
        runWithToastAsync { controller.allByBlog(blogId, offset, limit, order) }.getOrNull()

    suspend fun count(blogId: ULong): Long? =
        runWithToastAsync { controller.countByBlog(blogId) }.getOrNull()
}