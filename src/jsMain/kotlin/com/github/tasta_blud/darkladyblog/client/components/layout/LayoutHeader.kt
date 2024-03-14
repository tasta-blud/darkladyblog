package com.github.tasta_blud.darkladyblog.client.components.layout

import com.github.tasta_blud.darkladyblog.client.components.theme.languageSwitcher
import com.github.tasta_blud.darkladyblog.client.components.theme.navThemeSwitcher
import com.github.tasta_blud.darkladyblog.client.controllers.AppController
import io.kvision.core.Container
import io.kvision.form.form
import io.kvision.html.*
import io.kvision.i18n.gettext
import io.kvision.panel.SimplePanel
import io.kvision.state.bindEach
import io.kvision.theme.themeSwitcher

class LayoutHeader(appTitle: String = "") : SimplePanel("layout-header") {
    init {
        header {
            nav(className = "navbar navbar-expand-md fixed-top bg-body-tertiary") {
                div(className = "container-fluid px-5") {
                    link(label = appTitle, className = "navbar-brand", url = "#")
                    button(
                        text = appTitle,
                        className = "navbar-toggler",
                        icon = "navbar-toggler-icon",
                        type = ButtonType.BUTTON,
                        style = ButtonStyle.LINK
                    ) {
                        setAttribute("data-bs-toggle", "collapse")
                        setAttribute("data-bs-target", "#navbarCollapse")
                    }
                    div(className = "collapse navbar-collapse") {
                        id = "navbarCollapse"
                        ul(className = "navbar-nav me-auto mb-2 mb-md-0")
                            .bindEach(AppController.navbarMenu) { (label, url) ->
                                li(className = "nav-item") {
                                    link(label, url, className = "nav-link", dataNavigo = true)
                                }
                            }
                        ul(className = "navbar-nav mb-auto mx-1 mx-md-0") {
                            li(className = "nav-item mx-1 mx-md-0") {
                                form(className = "d-flex") {
                                    span(className = "input-group") {
                                        input(className = "form-control", type = InputType.SEARCH) {
                                            placeholder = gettext("Search")
                                        }
                                        button(
                                            className = "input-group-addon",
                                            style = ButtonStyle.OUTLINESUCCESS,
                                            type = ButtonType.SUBMIT,
                                            text = "",
                                            icon = "bi bi-search"
                                        )
                                    }
                                }
                            }
                            li(className = "nav-item mx-1 mx-md-0") {
                                div(className = "d-flex") {
                                    themeSwitcher(style = ButtonStyle.OUTLINESECONDARY, round = true)
                                }
                            }
                            li(className = "nav-item mx-1 mx-md-0") {
                                navThemeSwitcher()
                            }
                            li(className = "nav-item mx-1 mx-md-0") {
                                div(className = "d-flex") {
                                    languageSwitcher(*AppController.languages.toTypedArray())
                                }
                            }
                            li(className = "nav-item mx-1 mx-md-0") {
                                div(className = "d-flex") {
                                    inlineLogin()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Container.layoutHeader(
    appTitle: String = ""
): LayoutHeader =
    LayoutHeader(appTitle).also { add(it) }
