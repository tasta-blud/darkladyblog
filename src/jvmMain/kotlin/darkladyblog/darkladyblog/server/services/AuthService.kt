package darkladyblog.darkladyblog.server.services

import darkladyblog.darkladyblog.common.data.UserSession
import darkladyblog.darkladyblog.common.ex.NoSuchUserException
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.services.IAuthService
import io.ktor.server.application.ApplicationCall
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import org.koin.core.annotation.Factory

@Factory
class AuthService(val service: UserRepositoryService, val call: ApplicationCall) : IAuthService {

    override suspend fun loginUsername(username: String): Result<UserSession> =
        runCatching { service.getByUsername(username) }.mapCatching { loginUser(it).getOrThrow() }

    override suspend fun loginUsernamePassword(username: String, password: String): Result<UserSession> =
        runCatching {
            service.getByUsername(username)?.takeIf { service.checkPassword(it, password) }
        }.map { loginUser(it).getOrThrow() }

    override suspend fun loginUser(user: UserModel?): Result<UserSession> =
        user?.let { UserSession(it, "") }
            ?.runCatching { also { call.sessions.set<UserSession>(it) } }
            ?: Result.failure(NoSuchUserException(""))

    override suspend fun logout(): Boolean =
        runCatching { call.sessions.clear<UserSession>() }.isSuccess

    override suspend fun hasUser(): Boolean =
        runCatching { call.sessions.get<UserSession>() != null }.getOrDefault(false)

    override suspend fun getUser(): Result<UserSession> =
        runCatching { call.sessions.get<UserSession>() ?: throw NoSuchUserException("") }
}
