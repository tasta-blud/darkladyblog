@file:Suppress("D")

package darkladyblog.darkladyblog.client.components

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.base.store.BooleanRootStore
import darkladyblog.darkladyblog.client.components.controls.inputEditable
import darkladyblog.darkladyblog.client.components.editor.textareaEditor
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.store.RegistrationStore
import darkladyblog.darkladyblog.client.util.css
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.Registration
import darkladyblog.darkladyblog.common.model.Sex
import darkladyblog.darkladyblog.common.model.confirmPassword
import darkladyblog.darkladyblog.common.model.descriptionLongCompiled
import darkladyblog.darkladyblog.common.model.descriptionLongSource
import darkladyblog.darkladyblog.common.model.descriptionShortCompiled
import darkladyblog.darkladyblog.common.model.descriptionShortSource
import darkladyblog.darkladyblog.common.model.email
import darkladyblog.darkladyblog.common.model.nick
import darkladyblog.darkladyblog.common.model.password
import darkladyblog.darkladyblog.common.model.sex
import darkladyblog.darkladyblog.common.model.title
import darkladyblog.darkladyblog.common.model.username
import darkladyblog.darkladyblog.i18n.RegistrationTranslations
import dev.fritz2.core.RenderContext
import dev.fritz2.core.`for`
import dev.fritz2.core.name
import dev.fritz2.core.type
import dev.fritz2.core.value
import dev.fritz2.core.values
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.css.color

fun RenderContext.appRegistration(pageData: PageData, store: RegistrationStore) {
    store.data.render {
        div {
            val nick = store.map(Registration.nick())
            val username = store.map(Registration.username())
            val password = store.map(Registration.password())
            val confirmPassword = store.map(Registration.confirmPassword())
            val showPassword = BooleanRootStore(false)
            val sex = store.map(Registration.sex())
            val email = store.map(Registration.email())
            val title = store.map(Registration.title())
            val descriptionShortSource = store.map(Registration.descriptionShortSource())
            val descriptionShortCompiled = store.map(Registration.descriptionShortCompiled())
            val descriptionLongSource = store.map(Registration.descriptionLongSource())
            val descriptionLongCompiled = store.map(Registration.descriptionLongCompiled())
            store.render(this) {
                div("card") {
                    div("card-header") {
                        h1("display-4") {
                            span("fs-2") {
                                span("my-2") {
                                    span("btn-group") {
                                        attr("role", "group")
                                        Sex.entries.forEach { entry ->
                                            val optionId = sex.data.map { "option-" + entry.name.lowercase() }
                                            input("btn-check") {
                                                attr("id", optionId)
                                                type("radio")
                                                name("option")
                                                value(sex.data.map { entry.name })
                                                changes.values().map { Sex.valueOf(it) }
                                                    .map { entry } handledBy sex.update
                                            }
                                            label("btn") {
                                                className(sex.data.map {
                                                    if (it == entry) "btn-outline-primary" else "btn-outline-secondary"
                                                })
                                                `for`(optionId)
                                                i(entry.icon) {
                                                    css {
                                                        color = entry.color
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            +" "
                            inputEditable(nick, RegistrationTranslations.nick())
                        }
                    }
                    div("card-body") {
                        h5("card-title") {
                            h2("display-5 fst-italic") {
                                inputEditable(title, RegistrationTranslations.title())
                            }
                        }
                        p("card-text") {
                            p("lead my-3") {
                                textareaEditor(descriptionLongSource, descriptionLongCompiled)
                            }
                        }
                    }
                    ul("list-group list-group-flush") {
                        li("list-group-item") {
                            inputEditable(email, RegistrationTranslations.email())
                        }
                    }
                    ul("list-group list-group-flush") {
                        li("list-group-item") {
                            inputEditable(username, RegistrationTranslations.username())
                        }
                    }
                    ul("list-group list-group-flush") {
                        li("list-group-item") {
                            div("input-group") {
                                inputEditable(
                                    password,
                                    RegistrationTranslations.password(),
                                    showPassword.data.map { if (it) "text" else "password" })
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
                    }
                    ul("list-group list-group-flush") {
                        li("list-group-item") {
                            inputEditable(
                                confirmPassword,
                                RegistrationTranslations.confirm_password(),
                                showPassword.data.map { if (it) "text" else "password" }) {
                                className(showPassword.data.map { if (it) "" else "visually-hidden" })
                                password.data
                                    .combine(showPassword.data) { password, showPassword -> password to showPassword }
                                    .filter { (_, showPassword) -> showPassword }
                                    .map { (password, _) -> password } handledBy confirmPassword.update
                            }
                        }
                    }
                    div("card-footer") {
                        button("btn btn-primary") {
                            type("button")
                            i("bi bi-save") {}
                            clicks.combine(store.data) { _, d -> d } handledBy store.set
                            navigates(store.data.map { Pages.PAGE_PROFILE.page.withParameters() })
                        }
                    }
                }
            }
        }
    }
}

