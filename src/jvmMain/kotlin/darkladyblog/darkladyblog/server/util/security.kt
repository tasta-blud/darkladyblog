package darkladyblog.darkladyblog.server.util

import darkladyblog.darkladyblog.common.base.IdModel
import darkladyblog.darkladyblog.common.base.Owned
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.services.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.koin.ktor.plugin.scope

suspend fun ApplicationCall.getUser(): UserModel? = scope.get<AuthService>().getUser()?.user

suspend fun ApplicationCall.isMy(owned: IdModel<*>): Boolean =
    (owned as? Owned)?.let { getUser()?.username == it.createdBy.username } ?: true

suspend fun ApplicationCall.checkMy(owned: IdModel<*>, throws: Boolean = true): Boolean =
    isMy(owned).also { ok -> if (!ok && throws) respond(HttpStatusCode.Forbidden) }

