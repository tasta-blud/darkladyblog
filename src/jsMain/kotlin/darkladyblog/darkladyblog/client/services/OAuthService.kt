package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.intercept.runWithToastAsync
import darkladyblog.darkladyblog.common.config.OAuthClient
import darkladyblog.darkladyblog.common.controllers.IAuthController
import darkladyblog.darkladyblog.common.model.Principal
import dev.kilua.rpc.getService

object OAuthService {
    private val authController: IAuthController = getService<IAuthController>()

    suspend fun signIn(oAuth: OAuthClient): Result<Principal>? =
        runWithToastAsync { authController.signIn(/*oAuth.url*/) }.getOrNull()
}