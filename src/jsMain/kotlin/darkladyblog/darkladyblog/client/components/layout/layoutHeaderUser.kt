package darkladyblog.darkladyblog.client.components.layout

import darkladyblog.darkladyblog.client.base.store.BooleanRootStore
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.OAuthService
import darkladyblog.darkladyblog.client.store.LoginStore
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.config.OAuthClient
import darkladyblog.darkladyblog.common.model.Credentials
import darkladyblog.darkladyblog.common.model.password
import darkladyblog.darkladyblog.common.model.rememberMe
import darkladyblog.darkladyblog.common.model.username
import darkladyblog.darkladyblog.common.util.getGravatarURL
import darkladyblog.darkladyblog.i18n.HeaderMenuTranslations
import darkladyblog.darkladyblog.i18n.LoginTranslations
import dev.fritz2.core.Id
import dev.fritz2.core.RenderContext
import dev.fritz2.core.checked
import dev.fritz2.core.`for`
import dev.fritz2.core.id
import dev.fritz2.core.name
import dev.fritz2.core.placeholder
import dev.fritz2.core.required
import dev.fritz2.core.src
import dev.fritz2.core.states
import dev.fritz2.core.type
import dev.fritz2.core.value
import dev.fritz2.core.values
import kotlinx.coroutines.flow.map

fun RenderContext.layoutHeaderUser() {
    div("navbar-nav ms-auto me-0 mb-2 mb-lg-0") {
        PrincipalStore.renderIfNotLogged(this) {
            div("btn-group") {
                a("btn btn-tertiary") {
                    attr("aria-current", "page")
                    +HeaderMenuTranslations.register()
                    navigates(Pages.PAGE_REGISTRATION.page.withParameters())
                }
                a("btn btn-tertiary") {
                    attr("aria-current", "page")
                    +HeaderMenuTranslations.login()
                    navigates(Pages.PAGE_LOGIN.page.withParameters())
                }
                a("btn btn-tertiary dropdown-toggle dropdown-toggle-split") {
                    attr("role", "button")
                    attr("data-bs-toggle", "dropdown")
                    attr("aria-expanded", "false")
                    attr("aria-current", "page")
                    navigates(Pages.PAGE_LOGIN.page.withParameters())
                    div("dropdown-menu dropdown-menu-end") {

                        val id: String = "loginHeader" + Id.next()

                        val username = LoginStore.map(Credentials.username())
                        val password = LoginStore.map(Credentials.password())
                        val rememberMe = LoginStore.map(Credentials.rememberMe())

                        val showPassword = BooleanRootStore(false)
                        form {
                            div {
                                input("form-control") {
                                    name("username")
                                    type("text")
                                    id("loginUsername$id")
                                    placeholder(LoginTranslations.username())
                                    required(true)
                                    attr("autocomplete", "username")
                                    value(username.data)
                                    changes.values() handledBy username.update
                                }
                            }
                            div("form-floating") {
                                div("input-group") {
                                    input("form-control") {
                                        name("password")
                                        type(showPassword.data.map { if (it) "text" else "password" })
                                        id("loginPassword$id")
                                        placeholder(LoginTranslations.password())
                                        required(true)
                                        attr("autocomplete", "current-password")
                                        value(password.data)
                                        changes.values() handledBy password.update
                                    }
                                    button("btn") {
                                        type("button")
                                        className(showPassword.data.map { if (it) "btn-outline-light" else "btn-outline-secondary" })
                                        clicks handledBy showPassword.switch
                                        i("bi") {
                                            className(showPassword.data.map { if (it) "bi-eye" else "bi-eye-slash" })
                                        }
                                    }
                                }
                            }
                            div("form-check text-start") {
                                input("form-check-input") {
                                    type("checkbox")
                                    value("remember-me")
                                    id("loginRemember$id")
                                    checked(rememberMe.data)
                                    changes.states() handledBy rememberMe.update
                                }
                                label("form-check-label") {
                                    `for`("loginRemember$id")
                                    +LoginTranslations.remember_me()
                                }
                            }
                            button("btn btn-primary w-100 mb-2") {
                                type("button")
                                +LoginTranslations.sign_in()
                                navigates(LoginStore.login, Pages.PAGE_BLOGS.page.withParameters())
                            }
                            div("row border-top") {
                                div("col") {
                                    label {
                                    }
                                }
                            }
                            div("row") {
                                OAuthClient.entries.forEach { oAuthClient: OAuthClient ->
                                    div("") {
                                        div("input-group text-nowrap") {
                                            button("btn btn-outline-secondary") {
                                                i(oAuthClient.icon) {}
                                                clicks handledBy { it.preventDefault() }
                                                clicks handledBy { OAuthService.signIn(oAuthClient) }
                                            }
                                            button("btn btn-outline-secondary") {
                                                +oAuthClient.label
                                                clicks handledBy { it.preventDefault() }
                                                clicks handledBy { OAuthService.signIn(oAuthClient) }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        PrincipalStore.renderIfLogged(this) {
            div("dropdown") {
                a("btn btn-tertiary dropdown-toggle") {
                    id("navbarDropdownUser")
                    attr("role", "button")
                    attr("data-bs-toggle", "dropdown")
                    attr("aria-expanded", "false")
                    img { src(getGravatarURL(it.email, 20)) }
                    +" ${it.username}"
                }
                ul("dropdown-menu") {
                    attr("aria-labelledby", "navbarDropdownUser")
                    li {
                        a("dropdown-item") {
                            +HeaderMenuTranslations.profile()
                            navigates(Pages.PAGE_PROFILE.page.withParameters())
                        }
                    }
                    li {
                        hr("dropdown-divider") {}
                    }
                    li {
                        a("dropdown-item") {
                            +HeaderMenuTranslations.logout()
                            navigates(LoginStore.logout, Pages.PAGE_DEFAULT.page.withParameters())
                        }
                    }
                }
            }
        }
    }
}
