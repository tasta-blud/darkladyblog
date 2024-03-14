package com.github.tasta_blud.darkladyblog.client.controllers

import com.github.tasta_blud.darkladyblog.common.data.LoginFormData
import com.github.tasta_blud.darkladyblog.common.data.UserInfo
import com.github.tasta_blud.darkladyblog.common.services.ILoginService
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.i18n.gettext
import io.kvision.remote.getService
import io.kvision.routing.Routing
import io.kvision.routing.Strategy
import io.kvision.state.ObservableList
import io.kvision.state.ObservableValue
import io.kvision.state.observableListOf
import io.kvision.theme.Theme
import io.kvision.theme.ThemeManager
import io.kvision.toast.Toast
import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object AppController {
    const val DEBUG: Boolean = true
    val AppScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    val languages: List<Pair<String, String>> = listOf(
        "en" to "English",
        "ru" to "Russian",
    )
    const val PATH_ROOT: String = "/"
    const val PATH_LOGIN: String = "/login"

    val user: ObservableValue<UserInfo?> = ObservableValue(null)
    val navbarMenu: ObservableList<Pair<String, String>> = observableListOf()

    private val loginService = getService<ILoginService>()

    private lateinit var routing: Routing

    fun navigate(route: String) {
        routing.kvNavigate(route)
    }

    suspend fun ping(message: String): String =
        loginService.ping(message)

    suspend fun login(formData: LoginFormData = LoginFormData("", "", false), message: Boolean = true) {
        runCatching {
            loginService.login(formData).getOrThrow()
        }.onSuccess {
            this.user.value = it
            Toast.success(gettext("Welcome %1!", it.username), ToastOptions(ToastPosition.BOTTOMLEFT))
            navigate(PATH_ROOT)
        }.onFailure {
            if (message)
                Toast.danger(gettext("Wrong password or no such user"), ToastOptions(ToastPosition.BOTTOMLEFT))
        }
    }

    fun init() {
        ThemeManager.init(initialTheme = Theme.DARK)
        I18n.manager = DefaultI18nManager(mapOf(*languages.map { (language) ->
            language to io.kvision.require("i18n/messages-$language.json")
        }.toTypedArray()))
        routing = Routing.init(PATH_ROOT, true, Strategy.ALL)
    }

    fun destroy() {
        routing.destroy()
    }

    fun logout() {
        AppScope.launch {
            kotlin.runCatching {
                loginService.logout()
            }
            user.value = null
            routing.kvNavigate(PATH_ROOT)
        }
    }

}
