@file:Suppress("D")

package darkladyblog.darkladyblog.client.components

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.base.store.BooleanRootStore
import darkladyblog.darkladyblog.client.components.controls.inputEditable
import darkladyblog.darkladyblog.client.components.controls.linkUser
import darkladyblog.darkladyblog.client.components.editor.textareaEditor
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.store.UserStore
import darkladyblog.darkladyblog.client.util.css
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.Sex
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.model.descriptionLongCompiled
import darkladyblog.darkladyblog.common.model.descriptionLongSource
import darkladyblog.darkladyblog.common.model.descriptionShortCompiled
import darkladyblog.darkladyblog.common.model.descriptionShortSource
import darkladyblog.darkladyblog.common.model.email
import darkladyblog.darkladyblog.common.model.id
import darkladyblog.darkladyblog.common.model.nick
import darkladyblog.darkladyblog.common.model.sex
import darkladyblog.darkladyblog.common.model.title
import darkladyblog.darkladyblog.common.model.username
import darkladyblog.darkladyblog.i18n.ProfileTranslations
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

fun RenderContext.appUser(pageData: PageData, store: UserStore, isEditing: Boolean = false) {
    store.data.filter { it.id != null }.renderNotNull {
        div {
            val editing = BooleanRootStore(isEditing && store.current.id == PrincipalStore.current?.id)
            val id = store.map(UserModel.id())
            val nick = store.map(UserModel.nick())
            val username = store.map(UserModel.username())
            val sex = store.map(UserModel.sex())
            val email = store.map(UserModel.email())
            val title = store.map(UserModel.title())
            val descriptionShortSource = store.map(UserModel.descriptionShortSource())
            val descriptionShortCompiled = store.map(UserModel.descriptionShortCompiled())
            val descriptionLongSource = store.map(UserModel.descriptionLongSource())
            val descriptionLongCompiled = store.map(UserModel.descriptionLongCompiled())
            store.render(this) {
                div("card") {
                    div("card-header") {
                        h1("display-4") {
                            span("fs-2") {
                                editing.renderFalse(this) {
                                    sex.data.render {
                                        i(it.icon) {
                                            css {
                                                color = it.color
                                            }
                                        }
                                    }
                                }
                                editing.renderTrue(this) {
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
                            }
                            +" "
                            inputEditable(nick, editing, ProfileTranslations.nick())
                            +" "
                            span("mb-2 text-secondary") {
                                linkUser(username, id)
                            }
                            +" "
                            span("badge text-bg-tertiary rounded-pill") {
                                a("text-decoration-none") {
                                    +"#"
                                    id.data.renderText()
                                    navigates(id.data.map { Pages.PAGE_PROFILE.page.withParameters("id" to it.toString()) })
                                }
                            }
                            store.renderIfMe(this) {
                                span("badge btn-group") {
                                    button("btn btn-primary") {
                                        type("button")
                                        i("bi bi-pencil-square") {}
                                        clicks handledBy editing.switch
                                    }
                                }
                            }
                        }
                    }
                    div("card-body") {
                        h5("card-title") {
                            h2("display-5 fst-italic") {
                                inputEditable(title, editing, ProfileTranslations.title())
                            }
                        }
                        p("card-text") {
                            p("lead my-3") {
                                editing.renderFalse(this) {
                                    descriptionLongCompiled.data.renderText()
                                }
                                editing.renderTrue(this) {
                                    textareaEditor(descriptionLongSource, descriptionLongCompiled)
                                }
                            }
                        }
                    }
                    ul("list-group list-group-flush") {
                        mapOf(
                            ProfileTranslations.email() to email,
                        ).forEach { (label, lens) ->
                            li("list-group-item") {
                                div("row") {
                                    div("col-3") {
                                        +"$label:"
                                    }
                                    div("col-9") {
                                        lens.also { lens ->
                                            inputEditable(lens, editing, label)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    div("card-footer") {
                        editing.renderTrue(this) {
                            store.renderIfMe(this) {
                                button("btn btn-primary") {
                                    type("button")
                                    i("bi bi-save") {}
                                    clicks.combine(store.data) { _, d -> d } handledBy store.set
                                    clicks handledBy editing.switch
                                    navigates(id.data.map { Pages.PAGE_PROFILE.page.withParameters() })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

