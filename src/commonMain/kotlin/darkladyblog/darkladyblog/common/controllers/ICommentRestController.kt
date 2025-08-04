package darkladyblog.darkladyblog.common.controllers

import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.CommentModel
import dev.kilua.rpc.annotations.Method
import dev.kilua.rpc.annotations.RpcBinding
import dev.kilua.rpc.annotations.RpcService

@RpcService
interface ICommentRestController : IRestController<ULong, CommentModel> {

    @RpcBinding(Method.GET, "comments/search")
    override suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<CommentModel>

    @RpcBinding(Method.GET, "comments/all")
    override suspend fun all(offset: Long?, limit: Int?, order: Array<Sorting>): List<CommentModel>

    @RpcBinding(Method.GET, "comments/get")
    override suspend fun get(id: ULong): Result<CommentModel>

    @RpcBinding(Method.GET, "comments/create")
    override suspend fun create(model: CommentModel): ULong

    @RpcBinding(Method.PUT, "comments/update")
    override suspend fun update(model: CommentModel, id: ULong): Int

    @RpcBinding(Method.POST, "comments/save")
    override suspend fun save(model: CommentModel, getID: CommentModel.() -> ULong?): CommentModel

    @RpcBinding(Method.DELETE, "comments/delete")
    override suspend fun delete(id: ULong): Int

    @RpcBinding(Method.GET, "comments/count")
    override suspend fun count(): Long

    @RpcBinding(Method.GET, "comments/exists")
    override suspend fun exists(id: ULong): Boolean

    @RpcBinding(Method.GET, "comments/all/topic")
    suspend fun allByTopic(topicId: ULong, offset: Long?, limit: Int?, order: Array<Sorting>): List<CommentModel>

    suspend fun allByTopicAndParent(
        topicId: ULong,
        commentId: ULong,
        offset: Long?,
        limit: Int?,
        order: Array<Sorting>
    ): List<CommentModel>

    @RpcBinding(Method.GET, "count/topic")
    suspend fun countByTopic(topicId: ULong): Long

    @RpcBinding(Method.GET, "count/topic")
    suspend fun countByTopicAndParent(topicId: ULong, commentId: ULong): Long
}
