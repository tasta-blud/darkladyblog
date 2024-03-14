package com.github.tasta_blud.darkladyblog.client.app

import com.github.tasta_blud.darkladyblog.client.components.layout.layout
import com.github.tasta_blud.darkladyblog.client.controllers.AppController
import com.github.tasta_blud.darkladyblog.client.controllers.AppController.AppScope
import com.github.tasta_blud.darkladyblog.client.controllers.AppController.PATH_LOGIN
import com.github.tasta_blud.darkladyblog.client.controllers.AppController.PATH_ROOT
import com.github.tasta_blud.darkladyblog.client.pages.index
import com.github.tasta_blud.darkladyblog.client.pages.login
import io.kvision.Application
import io.kvision.i18n.gettext
import io.kvision.panel.root
import kotlinx.coroutines.launch

class App(private val appTitle: String = gettext("Tasta Blud")) : Application() {

    override fun start(state: Map<String, Any>) {
        AppController.init()
        root("kvapp") {
            layout(appTitle = appTitle) {
                login(gettext("Login"), PATH_LOGIN)
                index(gettext("Index"), PATH_ROOT)
            }
        }.apply {
            getElement()?.ownerDocument?.title = appTitle
        }
        AppScope.launch {
            AppController.login(message = false)
            lateInit()
        }
    }

    override fun dispose(): Map<String, Any> {
        AppController.destroy()
        return super.dispose()
    }

    private fun lateInit() {
        AppController.navbarMenu += gettext("Home") to PATH_ROOT
    }
}
