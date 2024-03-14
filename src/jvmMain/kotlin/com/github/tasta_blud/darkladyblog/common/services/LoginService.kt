package com.github.tasta_blud.darkladyblog.common.services

import com.github.tasta_blud.darkladyblog.common.data.LoginFormData
import com.github.tasta_blud.darkladyblog.common.data.UserInfo
import com.github.tasta_blud.darkladyblog.server.data.UserSession
import com.github.tasta_blud.darkladyblog.server.services.UserService
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.kvision.remote.ServiceException
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import org.koin.java.KoinJavaComponent.inject

@Suppress("ACTUAL_WITHOUT_EXPECT")
@Factory
actual class LoginService(private val call: ApplicationCall) : ILoginService {

    private val userService: UserService by inject(UserService::class.java)

    override suspend fun ping(message: String): String {
        println(message)
        return "Hello world from server!"
    }

    override suspend fun login(formData: LoginFormData): Result<UserInfo> =
        runBlocking {
            val session = call.sessions.get<UserSession>()
            if (session?.user != null) return@runBlocking Result.success(session.user)
            val user =
                userService.findUserByUsernameAndPassword(formData.username, formData.password)
                    ?: return@runBlocking Result.failure(ServiceException(""))
            val userInfo = UserInfo(user.username)
            call.sessions.set(UserSession(user = userInfo))
            return@runBlocking Result.success(userInfo)
        }

    override suspend fun logout(): Result<Unit> {
        call.sessions.set<UserSession>(null)
        return Result.success(Unit)
    }
}
