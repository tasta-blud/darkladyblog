package darkladyblog.darkladyblog.common.services

import darkladyblog.darkladyblog.common.data.UserSession
import darkladyblog.darkladyblog.common.model.UserModel
import dev.kilua.rpc.annotations.RpcService
import org.koin.core.component.KoinComponent

@RpcService
interface IAuthService : KoinComponent {
    suspend fun loginUsername(username: String): Result<UserSession>

    suspend fun loginUsernamePassword(username: String, password: String): Result<UserSession>

    suspend fun loginUser(user: UserModel?): Result<UserSession>

    suspend fun logout(): Boolean

    suspend fun hasUser(): Boolean

    suspend fun getUser(): Result<UserSession>
}
