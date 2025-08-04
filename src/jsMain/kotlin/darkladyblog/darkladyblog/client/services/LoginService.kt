package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.intercept.runWithToastAsync
import darkladyblog.darkladyblog.common.controllers.IAuthController
import darkladyblog.darkladyblog.common.model.Credentials
import darkladyblog.darkladyblog.common.model.Principal
import dev.kilua.rpc.getService

object LoginService {
    private val authController: IAuthController = getService<IAuthController>()

    suspend fun login(user: Credentials): Principal? =
        runWithToastAsync { authController.login(user).getOrNull() }.getOrNull()

    suspend fun logout() {
        runCatching { authController.logout() }
    }

    suspend fun me(): Principal? =
        runWithToastAsync { authController.me().getOrNull() }.getOrNull()

    suspend fun tryLogin(user: Credentials): Principal? =
        runWithToastAsync { authController.tryLogin(user).getOrNull() }.getOrNull()

    suspend fun reLogin(): Principal? =
        runWithToastAsync { authController.reLogin().getOrNull() }.getOrNull()
}