package darkladyblog.darkladyblog.common.controllers

import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.BlogModel
import dev.kilua.rpc.annotations.Method
import dev.kilua.rpc.annotations.RpcBinding
import dev.kilua.rpc.annotations.RpcService

@RpcService
interface IBlogRestController : IRestController<ULong, BlogModel> {

    @RpcBinding(Method.GET, "blogs/search")
    override suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<BlogModel>

    @RpcBinding(Method.GET, "blogs/all")
    override suspend fun all(offset: Long?, limit: Int?, order: Array<Sorting>): List<BlogModel>

    @RpcBinding(Method.GET, "blogs/get")
    override suspend fun get(id: ULong): Result<BlogModel>

    @RpcBinding(Method.GET, "blogs/create")
    override suspend fun create(model: BlogModel): ULong

    @RpcBinding(Method.PUT, "blogs/update")
    override suspend fun update(model: BlogModel, id: ULong): Int

    @RpcBinding(Method.POST, "blogs/save")
    override suspend fun save(model: BlogModel, getID: BlogModel.() -> ULong?): BlogModel

    @RpcBinding(Method.DELETE, "blogs/delete")
    override suspend fun delete(id: ULong): Int

    @RpcBinding(Method.GET, "blogs/count")
    override suspend fun count(): Long

    @RpcBinding(Method.GET, "blogs/exists")
    override suspend fun exists(id: ULong): Boolean
}
