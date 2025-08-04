package darkladyblog.darkladyblog.common.controllers

import darkladyblog.darkladyblog.common.model.Credentials
import darkladyblog.darkladyblog.common.model.Principal
import dev.kilua.rpc.annotations.Method
import dev.kilua.rpc.annotations.RpcBinding
import dev.kilua.rpc.annotations.RpcService
import org.koin.core.component.KoinComponent

@RpcService
interface IAuthController : KoinComponent {

    @RpcBinding(Method.POST, "auth/signin")
    suspend fun signIn(): Result<Principal>

    @RpcBinding(Method.POST, "auth/login")
    suspend fun login(user: Credentials): Result<Principal>

    @RpcBinding(Method.POST, "auth/tryLogin")
    suspend fun tryLogin(user: Credentials): Result<Principal>

    @RpcBinding(Method.POST, "auth/reLogin")
    suspend fun reLogin(): Result<Principal>

    @RpcBinding(Method.POST, "auth/logout")
    suspend fun logout(): Boolean

    @RpcBinding(Method.POST, "auth/me")
    suspend fun me(): Result<Principal>
}
