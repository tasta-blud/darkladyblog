package darkladyblog.darkladyblog.common.controllers

import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.TopicModel
import dev.kilua.rpc.annotations.Method
import dev.kilua.rpc.annotations.RpcBinding
import dev.kilua.rpc.annotations.RpcService

@RpcService
interface ITopicRestController : IRestController<ULong, TopicModel> {

    @RpcBinding(Method.GET, "topics/search")
    override suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<TopicModel>

    @RpcBinding(Method.GET, "topics/all")
    override suspend fun all(offset: Long?, limit: Int?, order: Array<Sorting>): List<TopicModel>

    @RpcBinding(Method.GET, "topics/get")
    override suspend fun get(id: ULong): Result<TopicModel>

    @RpcBinding(Method.GET, "topics/create")
    override suspend fun create(model: TopicModel): ULong

    @RpcBinding(Method.PUT, "topics/update")
    override suspend fun update(model: TopicModel, id: ULong): Int

    @RpcBinding(Method.POST, "topics/save")
    override suspend fun save(model: TopicModel, getID: TopicModel.() -> ULong?): TopicModel

    @RpcBinding(Method.DELETE, "topics/delete")
    override suspend fun delete(id: ULong): Int

    @RpcBinding(Method.GET, "topics/count")
    override suspend fun count(): Long

    @RpcBinding(Method.GET, "topics/exists")
    override suspend fun exists(id: ULong): Boolean

    @RpcBinding(Method.GET, "topics/all/blog")
    suspend fun allByBlog(
        blogId: ULong,
        offset: Long?,
        limit: Int?,
        order: Array<Sorting>
    ): List<TopicModel>

    @RpcBinding(Method.GET, "topics/count/blog")
    suspend fun countByBlog(blogId: ULong): Long
}
