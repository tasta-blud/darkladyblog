package darkladyblog.darkladyblog.client.components.app

import darkladyblog.darkladyblog.client.base.rest.RestListStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.services.CommentService
import darkladyblog.darkladyblog.common.controllers.ICommentRestController
import darkladyblog.darkladyblog.common.model.app.CommentModel
import dev.fritz2.core.RenderContext
import kotlinx.coroutines.flow.map

fun RenderContext.appComments(
    pageData: PageData,
    listStore: RestListStore<ULong, CommentModel, CommentService, ICommentRestController>,
    parentId: ULong? = null
) {
    ul("list-unstyled ps-5 border border-start-5 border-end-0 border-top-0 border-bottom-0", "comment_list_$parentId") {
        listStore.data.map { list -> list.filter { it.parent?.id == parentId } }
            .map { it.map { c -> c.id } }.renderEach {
                li("comment", "comment_$it") {
                    attr("data-bs-toggle", "collapse")
                    attr("data-bs-target", "#comment_list_$it")
                    appComment(pageData, listStore, it, parentId)
                    appComments(pageData, listStore, it)
                }
        }
    }
}

