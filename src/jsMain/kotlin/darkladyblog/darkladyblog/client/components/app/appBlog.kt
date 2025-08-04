package darkladyblog.darkladyblog.client.components.app

import darkladyblog.darkladyblog.client.base.rest.RestStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.base.store.BooleanRootStore
import darkladyblog.darkladyblog.client.components.confirm
import darkladyblog.darkladyblog.client.components.controls.inputEditable
import darkladyblog.darkladyblog.client.components.controls.linkUser
import darkladyblog.darkladyblog.client.components.editor.textareaEditor
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.BlogService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.controllers.IBlogRestController
import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.model.app.alias
import darkladyblog.darkladyblog.common.model.app.createdAt
import darkladyblog.darkladyblog.common.model.app.createdAtString
import darkladyblog.darkladyblog.common.model.app.createdBy
import darkladyblog.darkladyblog.common.model.app.descriptionLongCompiled
import darkladyblog.darkladyblog.common.model.app.descriptionLongSource
import darkladyblog.darkladyblog.common.model.app.descriptionShortCompiled
import darkladyblog.darkladyblog.common.model.app.descriptionShortSource
import darkladyblog.darkladyblog.common.model.app.id
import darkladyblog.darkladyblog.common.model.app.title
import darkladyblog.darkladyblog.common.model.app.updatedAt
import darkladyblog.darkladyblog.common.model.app.updatedAtString
import darkladyblog.darkladyblog.common.model.app.updatedBy
import darkladyblog.darkladyblog.common.util.toAlias
import darkladyblog.darkladyblog.i18n.CommonTranslations
import dev.fritz2.core.RenderContext
import dev.fritz2.core.type
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

fun RenderContext.appBlog(
    pageData: PageData,
    store: RestStore<ULong, BlogModel, BlogService, IBlogRestController>,
    isEditing: Boolean = false
) {
    div {
        val editing = BooleanRootStore(isEditing || store.current.id == null)
        val id = store.map(BlogModel.id())
        val title = store.map(BlogModel.title())
        val descriptionShortSource = store.map(BlogModel.descriptionShortSource())
        val descriptionShortCompiled = store.map(BlogModel.descriptionShortCompiled())
        val descriptionLongSource = store.map(BlogModel.descriptionLongSource())
        val descriptionLongCompiled = store.map(BlogModel.descriptionLongCompiled())
        val alias = store.map(BlogModel.alias())
        title.data.map { it.toAlias() } handledBy alias.update
        val createdBy = store.map(BlogModel.createdBy())
        val createdAt = store.map(BlogModel.createdAt())
        val createdAtString = store.map(BlogModel.createdAtString())
        val updatedBy = store.map(BlogModel.updatedBy())
        val updatedAt = store.map(BlogModel.updatedAt())
        val updatedAtString = store.map(BlogModel.updatedAtString())
        div("p-4 p-md-5 mb-4 rounded text-body-emphasis bg-body-secondary") {
            store.render(this) {
                h1("display-4 fst-italic") {
                    inputEditable(title, editing, CommonTranslations.title())
                    PrincipalStore.renderIfMy(this, store) { principal, model ->
                        span("badge btn-group") {
                            button("btn btn-primary") {
                                type("button")
                                i("bi bi-pencil-square") {}
                                clicks handledBy editing.switch
                            }
                            confirm(
                                store.data,
                                store.delete,
                                store.data.map { "danger" },
                                title.data,
                                route = store.data.map { Pages.PAGE_BLOG.page.withParameters() },
                                buttonContent = {
                                    i("bi bi-trash") {}
                                }) {
                                title.data.renderText()
                            }
                        }
                    }
                }
                h2("display-5") {
                    +" "
                    code {
                        createdAtString.data.renderText()
                    }
                    +" "
                    linkUser(createdBy)
                }
                p("lead my-3") {
                    editing.renderFalse(this) {
                        descriptionShortCompiled.data.renderText()
                    }
                    editing.renderTrue(this) {
                        textareaEditor(descriptionLongSource, descriptionLongCompiled)
                    }
                }
                editing.renderTrue(this) {
                    PrincipalStore.renderIfMy(this, store) { principal, model ->
                        button("btn btn-primary") {
                            type("button")
                            i("bi bi-save") {}
                            clicks.combine(store.data) { _, d -> d } handledBy store.set
                            clicks handledBy editing.switch
                            navigates(id.data.map { Pages.PAGE_BLOG.page.withParameters("id" to it.toString()) })
                        }
                    }
                }
            }
        }
        div("col-12") {
            store.render(this) { blog ->
                appTopics(pageData, store)
            }
        }
    }
}