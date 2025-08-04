package darkladyblog.darkladyblog.common.controllers

import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.UserModel
import dev.kilua.rpc.annotations.Method
import dev.kilua.rpc.annotations.RpcBinding
import dev.kilua.rpc.annotations.RpcService

@RpcService
interface IUserRestController : IRestController<ULong, UserModel> {

    @RpcBinding(Method.GET, "users/search")
    override suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<UserModel>

    @RpcBinding(Method.GET, "users/all")
    override suspend fun all(offset: Long?, limit: Int?, order: Array<Sorting>): List<UserModel>

    @RpcBinding(Method.GET, "users/get")
    override suspend fun get(id: ULong): Result<UserModel>

    @RpcBinding(Method.GET, "users/create")
    override suspend fun create(model: UserModel): ULong

    @RpcBinding(Method.PUT, "users/update")
    override suspend fun update(model: UserModel, id: ULong): Int

    @RpcBinding(Method.POST, "users/save")
    override suspend fun save(model: UserModel, getID: UserModel.() -> ULong?): UserModel

    @RpcBinding(Method.DELETE, "users/delete")
    override suspend fun delete(id: ULong): Int

    @RpcBinding(Method.GET, "users/count")
    override suspend fun count(): Long

    @RpcBinding(Method.GET, "users/exists")
    override suspend fun exists(id: ULong): Boolean
}
