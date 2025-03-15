package darkladyblog.darkladyblog.server.services

import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.data.UserSession
import io.ktor.server.application.ApplicationCall
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import org.koin.core.component.KoinComponent
import org.koin.ktor.plugin.RequestScope

@Scope(RequestScope::class)
@Scoped
class AuthService(private val service: UserRepositoryService, private val call: ApplicationCall) : KoinComponent {

    suspend fun login(username: String): Boolean {
        return login((service.getByUsername(username) ?: return false))
    }

    suspend fun login(username: String, password: String): Boolean {
        return (service.getByUsername(username) ?: return false).let {
            if (service.checkPassword(it, password)) login(it) else false
        }
    }

    suspend fun login(user: UserModel?): Boolean {
        call.sessions.set<UserSession>(UserSession(user, ""))
        return true
    }

    suspend fun logout() {
        call.sessions.clear<UserSession>()
    }

    suspend fun hasUser(): Boolean {
        return call.sessions.get<UserSession>() != null
    }

    suspend fun getUser(): UserSession? {
        return call.sessions.get<UserSession>()
    }
}
