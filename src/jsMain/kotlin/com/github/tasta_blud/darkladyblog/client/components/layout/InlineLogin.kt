package com.github.tasta_blud.darkladyblog.client.components.layout

import com.github.tasta_blud.darkladyblog.client.controllers.AppController
import com.github.tasta_blud.darkladyblog.client.controllers.AppController.PATH_ROOT
import com.github.tasta_blud.darkladyblog.client.util.hr
import com.github.tasta_blud.darkladyblog.client.util.routing
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.i18n.gettext
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import kotlinx.coroutines.launch

class InlineLogin : SimplePanel("inline-login") {
    init {
        bind(AppController.user) {
            if (it == null) {
                link(gettext("Login"), AppController.PATH_LOGIN, className = "nav-link", dataNavigo = true)
            } else {
                div(className = "dropdown") {
                    button(
                        className = "dropdown-toggle",
                        text = it.username,
                        type = ButtonType.BUTTON,
                        style = ButtonStyle.OUTLINEINFO
                    ) {
                        setAttribute("data-bs-toggle", "dropdown")
                        setAttribute("aria-expanded", "false")
                    }
                    ul(className = "dropdown-menu dropdown-menu-end") {
                        li {
                            link(
                                className = "dropdown-item",
                                label = gettext("Profile"),
                                url = PATH_ROOT,
                                icon = "bi bi-person",
                                dataNavigo = true
                            )
                        }
                        li {
                            hr(className = "dropdown-divider")
                        }
                        li {
                            link(
                                className = "dropdown-item",
                                label = gettext("Logout"),
                                url = PATH_ROOT,
                                icon = "bi bi-person",
                                dataNavigo = true
                            ).onClick {
                                AppController.logout()
                            }
                        }
                    }
                }
            }
            AppController.AppScope.launch {
                routing.updatePageLinks()
            }
        }
    }
}

fun Container.inlineLogin(): InlineLogin =
    InlineLogin().also { add(it) }
