package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.OAuthService
import darkladyblog.darkladyblog.client.store.LoginStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.config.OAuthClient
import darkladyblog.darkladyblog.common.model.Credentials
import darkladyblog.darkladyblog.common.model.password
import darkladyblog.darkladyblog.common.model.rememberMe
import darkladyblog.darkladyblog.common.model.username
import darkladyblog.darkladyblog.i18n.ApplicationTranslations
import darkladyblog.darkladyblog.i18n.LoginTranslations
import dev.fritz2.core.Id
import dev.fritz2.core.RenderContext
import dev.fritz2.core.checked
import dev.fritz2.core.`for`
import dev.fritz2.core.id
import dev.fritz2.core.name
import dev.fritz2.core.placeholder
import dev.fritz2.core.states
import dev.fritz2.core.type
import dev.fritz2.core.value
import dev.fritz2.core.values

fun RenderContext.pageLogin(pageData: PageData, id: String = "login" + Id.next()) {
    val username = LoginStore.map(Credentials.username())
    val password = LoginStore.map(Credentials.password())
    val rememberMe = LoginStore.map(Credentials.rememberMe())
    div("d-flex align-items-center py-4 bg-body-tertiary") {
        main("form-signin w-100 m-auto") {
            form {
                h1("h3 mb-3 fw-normal") { +LoginTranslations.please_sign_in() }
                div("form-floating") {
                    input("form-control") {
                        name("username")
                        type("text")
                        id("loginUsername$id")
                        placeholder(LoginTranslations.username())
                        attr("autocomplete", "username")
                        value(username.data)
                        changes.values() handledBy username.update
                    }
                    label {
                        `for`("loginUsername$id")
                        +LoginTranslations.username()
                    }
                }
                div("form-floating") {
                    input("form-control") {
                        name("password")
                        type("password")
                        id("loginPassword$id")
                        placeholder(LoginTranslations.password())
                        attr("autocomplete", "current-password")
                        value(password.data)
                        changes.values() handledBy password.update
                    }
                    label {
                        `for`("loginPassword$id")
                        +LoginTranslations.password()
                    }
                }
                div("form-check text-start my-3") {
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
                button("btn btn-primary w-100 py-2") {
                    type("button")
                    +LoginTranslations.sign_in()
                    navigates(LoginStore.login, Pages.PAGE_BLOGS.page.withParameters())
                }
                div("row border-top") {
                    div("col text-center mt-3 mb-2") {
                        label {
                            +LoginTranslations.sign_in_please()
                        }
                    }
                }
                div("row justify-content-center") {
                    OAuthClient.entries.forEach { oAuthClient: OAuthClient ->
                        div("col-2 justify-content-center") {
                            div("input-group") {
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
                p("mt-5 mb-3 text-body-secondary") { +ApplicationTranslations.application_copyright() }
            }
        }
    }
}
