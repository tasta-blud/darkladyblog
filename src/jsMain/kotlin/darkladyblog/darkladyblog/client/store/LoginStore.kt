package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.client.services.Alerts
import darkladyblog.darkladyblog.client.services.LoginService
import darkladyblog.darkladyblog.common.model.Credentials
import darkladyblog.darkladyblog.i18n.LoginTranslations
import dev.fritz2.core.Handler

object LoginStore : RootStoreBase<Credentials>(Credentials(), id = "credentials") {

    val login: Handler<Unit> = handle { credentials ->
        credentials
            .let { LoginService.doLogin(it) }
            .also { PrincipalStore.update(it) }
            .also { PrincipalStore.data handledBy PrincipalStore.update }
            ?.let { Credentials(it.username, it.password) }
            ?.also { Alerts.success(LoginTranslations.welcome_w(it.username)) }
            ?: initialData
    }

    val tryLogin: Handler<Unit> = handle { credentials ->
        credentials
            .let { PrincipalStore.current ?: LoginService.tryLogin() }
            .also { PrincipalStore.update(it) }
            .also { PrincipalStore.data handledBy PrincipalStore.update }
            ?.let { Credentials(it.username, it.password) }
            ?.also { Alerts.success(LoginTranslations.welcome_w(it.username)) }
            ?: initialData
    }

    val logout: Handler<Unit> = handle {
        LoginService.doLogout()
            .also { PrincipalStore.update(null) }
            .also { PrincipalStore.data handledBy PrincipalStore.update }
            .let { initialData }
    }
}