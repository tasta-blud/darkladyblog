package darkladyblog.darkladyblog.server.util

import darkladyblog.darkladyblog.common.base.IdModel
import darkladyblog.darkladyblog.common.base.Owned
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.services.AuthService
import dev.kilua.rpc.SecurityException
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent

val KoinComponent.loggedUser: UserModel?
    get() =
        runBlocking { getLoggerUser() }

suspend inline fun KoinComponent.getLoggerUser(): UserModel? =
    getKoin().get<AuthService>().getUser().map { loggedUser }.getOrNull()

suspend inline fun KoinComponent.isMy(model: IdModel<*>): Boolean =
    (model as? Owned<*>)?.let { getLoggerUser()?.username == it.createdBy.username } ?: false

suspend inline fun <reified T : KoinComponent, reified ID : Any, reified M : IdModel<ID>, reified R> T.ifMy(
    model: M,
    block: T.(M) -> R
): R? =
    model.takeIf { getLoggerUser()?.username == (it as? Owned<*>)?.createdBy?.username }
        ?.let { block(it) }

suspend inline fun KoinComponent.checkMy(model: IdModel<*>, throws: Boolean = true): Boolean =
    isMy(model).also { ok -> if (!ok && throws) throw SecurityException("${model::class.simpleName} ${model.id}") }

