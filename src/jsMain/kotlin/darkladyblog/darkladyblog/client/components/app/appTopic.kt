@file:Suppress("D")

package darkladyblog.darkladyblog.client.components.app

import darkladyblog.darkladyblog.client.base.rest.RestListStore
import darkladyblog.darkladyblog.client.base.rest.RestStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.base.route.element
import darkladyblog.darkladyblog.client.base.store.BooleanRootStore
import darkladyblog.darkladyblog.client.components.confirm
import darkladyblog.darkladyblog.client.components.controls.inputEditable
import darkladyblog.darkladyblog.client.components.controls.linkUser
import darkladyblog.darkladyblog.client.components.editor.textareaEditor
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.CommentService
import darkladyblog.darkladyblog.client.services.TopicService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.controllers.ICommentRestController
import darkladyblog.darkladyblog.common.controllers.ITopicRestController
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.model.app.TopicModel
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

fun RenderContext.appTopic(
    pageData: PageData,
    store: RestStore<ULong, TopicModel, TopicService, ITopicRestController>,
    isEditing: Boolean = false
) {
    val isTabCommentsActive: Boolean =
        pageData.element != null && ((pageData.element ?: return).startsWith("#comment_"))
    val isTabTopicActive: Boolean = !isTabCommentsActive
    div {
        val editing = BooleanRootStore(isEditing || store.current.id == null)
        val id = store.map(TopicModel.id())
        val title = store.map(TopicModel.title())
        val descriptionShortSource = store.map(TopicModel.descriptionShortSource())
        val descriptionShortCompiled = store.map(TopicModel.descriptionShortCompiled())
        val descriptionLongSource = store.map(TopicModel.descriptionLongSource())
        val descriptionLongCompiled = store.map(TopicModel.descriptionLongCompiled())
        val alias = store.map(TopicModel.alias())
        title.data.map { it.toAlias() } handledBy alias.update
        val createdBy = store.map(TopicModel.createdBy())
        val createdAt = store.map(TopicModel.createdAt())
        val createdAtString = store.map(TopicModel.createdAtString())
        val updatedBy = store.map(TopicModel.updatedBy())
        val updatedAt = store.map(TopicModel.updatedAt())
        val updatedAtString = store.map(TopicModel.updatedAtString())
        store.render(this) {
            ul("nav nav-tabs") {
                li("nav-item") {
                    a("nav-link") {
                        className(if (isTabTopicActive) "active" else "")
                        title.data.map { CommonTranslations.topic_w(store.current.title) }.renderText()
                        attr("data-bs-toggle", "tab")
                        attr("data-bs-target", "#topic_tab")
                    }
                }
                li("nav-item") {
                    a("nav-link") {
                        className(if (isTabCommentsActive) "active" else "")
                        +CommonTranslations.comments()
                        attr("data-bs-toggle", "tab")
                        attr("data-bs-target", "#topic_comments")
                    }
                }
            }
            div("tab-content") {
                div("tab-pane fade", "topic_tab") {
                    className(if (isTabTopicActive) "show active" else "")
                    article("mb-4") {
                        h2("display-5 link-body-emphasis mb-1") {
                            inputEditable(title, editing, CommonTranslations.title())
                        }
                        PrincipalStore.renderIfMy(this, store) { principal, model ->
                            div("btn-group") {
                                div("btn-group") {
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
                                        route = store.data.map { Pages.PAGE_BLOG.page.withParameters("id" to it.blog.id.toString()) },
                                        buttonContent = {
                                            i("bi bi-trash") {}
                                        }) {
                                        title.data.renderText()
                                    }
                                }
                            }
                        }
                        div("mb-2 text-secondary") {
                            +" "
                            code {
                                createdAtString.data.renderText()
                            }
                            +" "
                            linkUser(createdBy)
                        }
                        p {
                            editing.renderFalse(this) {
                                descriptionLongCompiled.data.renderText()
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
                                    navigates(id.data.map { Pages.PAGE_TOPIC.page.withParameters("id" to it.toString()) })
                                }
                            }
                        }
                    }
                }
                div("tab-pane fade", "topic_comments") {
                    className(if (isTabCommentsActive) "show active" else "")
                    val listStore = object : RestListStore<ULong, CommentModel, CommentService, ICommentRestController>(
                        CommentService,
                        order = arrayOf(
                            Sorting(ColumnName("updated_at"), SortDirection.DESC),
                            Sorting(ColumnName("created_at"), SortDirection.DESC),
                            Sorting(ColumnName("id"), SortDirection.DESC),
                        )
                    ) {
                        override suspend fun countIt(): Long =
                            store.current.id.let { id ->
                                if (id != null)
                                    restService.count(id)
                                else
                                    restService.count()
                            } ?: 0L

                        override suspend fun all(): List<CommentModel>? =
                            store.current.id.let { id ->
                                if (id != null)
                                    restService.all(id, order = order)
                                else
                                    restService.all(order = order)
                            }
                    }
                    appComments(pageData, listStore)
                }
            }
        }
    }
}