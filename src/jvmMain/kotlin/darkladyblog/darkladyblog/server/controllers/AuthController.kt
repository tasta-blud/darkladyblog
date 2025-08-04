package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.controllers.IAuthController
import darkladyblog.darkladyblog.common.ex.NoSuchUserException
import darkladyblog.darkladyblog.common.model.Credentials
import darkladyblog.darkladyblog.common.model.Principal
import darkladyblog.darkladyblog.common.util.filterNotNull
import darkladyblog.darkladyblog.server.services.AuthService
import io.ktor.server.application.ApplicationCall
import org.koin.core.annotation.Factory

@Factory
class AuthController(val call: ApplicationCall, val authService: AuthService) : IAuthController {

    override suspend fun signIn(): Result<Principal> =
        runCatching { TODO() }

    override suspend fun login(user: Credentials): Result<Principal> =
        authService.loginUsernamePassword(user.username, user.password).map { it.user }
            .filterNotNull { NoSuchUserException(user.username) }.map { Principal.fromUser(it) }

    override suspend fun tryLogin(user: Credentials): Result<Principal> =
        authService.loginUsername(user.username).map { it.user }
            .filterNotNull { NoSuchUserException(user.username) }.map { Principal.fromUser(it) }

    override suspend fun reLogin(): Result<Principal> =
        authService.getUser().map { it.user }
            .filterNotNull { NoSuchUserException("") }.map { Principal.fromUser(it) }

    override suspend fun logout(): Boolean =
        authService.logout()

    override suspend fun me(): Result<Principal> =
        authService.getUser().map { it.user }
            .filterNotNull { NoSuchUserException("") }.map { Principal.fromUser(it) }

}
