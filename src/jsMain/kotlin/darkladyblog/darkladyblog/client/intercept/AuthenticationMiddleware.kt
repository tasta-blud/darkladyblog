package darkladyblog.darkladyblog.client.intercept

import darkladyblog.darkladyblog.client.store.LoginStore
import darkladyblog.darkladyblog.common.model.Credentials
import dev.fritz2.remote.Authentication
import dev.fritz2.remote.Request

class AuthenticationMiddleware : Authentication<Credentials>() {
    override fun addAuthentication(request: Request, principal: Credentials?): Request {
        // TODO
        return request
    }

    override fun authenticate() {
        complete(LoginStore.current)
    }
}