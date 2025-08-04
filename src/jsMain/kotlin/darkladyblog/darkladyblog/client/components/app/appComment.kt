package darkladyblog.darkladyblog.client.components.app

import darkladyblog.darkladyblog.client.base.rest.RestListStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.base.store.BooleanRootStore
import darkladyblog.darkladyblog.client.components.confirm
import darkladyblog.darkladyblog.client.components.controls.inputEditable
import darkladyblog.darkladyblog.client.components.controls.linkUser
import darkladyblog.darkladyblog.client.components.editor.textareaEditor
import darkladyblog.darkladyblog.client.data.PageRouter
import darkladyblog.darkladyblog.client.services.CommentService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.controllers.ICommentRestController
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.model.app.createdAtString
import darkladyblog.darkladyblog.common.model.app.createdBy
import darkladyblog.darkladyblog.common.model.app.descriptionLongCompiled
import darkladyblog.darkladyblog.common.model.app.descriptionLongSource
import darkladyblog.darkladyblog.common.model.app.descriptionShortCompiled
import darkladyblog.darkladyblog.common.model.app.descriptionShortSource
import darkladyblog.darkladyblog.common.model.app.id
import darkladyblog.darkladyblog.common.model.app.title
import darkladyblog.darkladyblog.i18n.CommonTranslations
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Store
import dev.fritz2.core.lensOf
import dev.fritz2.core.type
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

fun RenderContext.appComment(
    pageData: PageData,
    listStore: RestListStore<ULong, CommentModel, CommentService, ICommentRestController>,
    commentId: ULong?,
    parentId: ULong?,
    isEditing: Boolean = false
) {
    listStore.data.map { list -> list.firstOrNull { it.id == commentId } }.renderNotNull {
        div("card") {
            val store: Store<CommentModel> = listStore.map(
                lensOf(
                    (parentId ?: "").toString(),
                    { list -> list.first { it.id == commentId } },
                    { list, comment -> list + comment }
                ))
            val editing = BooleanRootStore(isEditing || store.current.id == null)
            val id = store.map(CommentModel.id())
            val title = store.map(CommentModel.title())
            val descriptionShortSource = store.map(CommentModel.descriptionShortSource())
            val descriptionShortCompiled = store.map(CommentModel.descriptionShortCompiled())
            val descriptionLongSource = store.map(CommentModel.descriptionLongSource())
            val descriptionLongCompiled = store.map(CommentModel.descriptionLongCompiled())
            val createdAtString = store.map(CommentModel.createdAtString())
            val createdBy = store.map(CommentModel.createdBy())
            div("card-body") {
                h5("card-title") {
                    span("d-inline-block") {
                        attr("data-bs-toggle", "popover")
                        attr("data-bs-trigger", "hover focus")
                        attr("data-bs-content", store.data.map { it.parent?.title ?: "" })
                        inputEditable(title, editing, CommonTranslations.title())
                    }
                }
                h6("card-subtitle mb-2 text-body-secondary") {
                    createdAtString.data.renderText()
                    +" "
                    linkUser(createdBy)
                }
                p("card-text") {
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
                            clicks.combine(store.data) { _, d -> d } handledBy store.update
                            clicks handledBy editing.switch
                            navigates(id.data.map { PageRouter.withExtraElement("#comment_$it") })
                        }
                    }
                }
                a("card-link") {
                    i("bi bi-arrow-90deg-up") {}
                    navigates(PageRouter.withExtraElement("#comment_$parentId"))
                }
                PrincipalStore.renderIfLogged(this) {
                    a("card-link") {
                        i("bi bi-reply") {}
                        clicks handledBy { it.preventDefault() }
                        clicks.combine(store.data) { _, comment -> comment }.map { comment ->
                            CommentModel(
                                comment.topic,
                                comment,
                                "Re: ${comment.title}",
                                "@${comment.createdBy.nick}, ",
                                "@${comment.createdBy.nick}, ",
                                "@${comment.createdBy.nick}, ",
                                "@${comment.createdBy.nick}, ",
                                PrincipalStore.current?.let { UserModel.fromPrincipal(it) } ?: UserModel.NULL_USER
                            )
                        } handledBy listStore.add
                        navigates(id.data.map { PageRouter.withExtraElement("#comment_$it") })
                    }
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
                                listStore.remove,
                                store.data.map { "danger" },
                                title.data,
                                route = store.data.map { PageRouter.withExtraElement("#comment_$parentId") },
                                buttonContent = {
                                    i("bi bi-trash") {}
                                }) {
                                title.data.renderText()
                            }
                        }
                    }
                }
            }
        }
    }
    listStore.data.map { list -> list.firstOrNull { it.id == commentId } }.renderIf({ it == null }) {
        div("null-comment") {}
    }
}