package darkladyblog.darkladyblog.client.components.app

import darkladyblog.darkladyblog.client.base.rest.RootStorePageableBase
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.controls.linkUser
import darkladyblog.darkladyblog.client.components.pagination
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.app.search.SearchResult
import darkladyblog.darkladyblog.common.model.app.search.SearchResultType
import dev.fritz2.core.RenderContext

fun RenderContext.appSearch(pageData: PageData, listStore: RootStorePageableBase<SearchResult>) {
    div("col-12") {
        listStore.render(this) {
            pagination(listStore.paginationStore)
            div("div") {
                listStore.renderEach(this) { result ->
                    article("mb-4") {
                        val route = when (result.type) {
                            SearchResultType.BLOG -> Pages.PAGE_BLOG
                            SearchResultType.TOPIC -> Pages.PAGE_TOPIC
                            SearchResultType.COMMENT -> Pages.PAGE_TOPIC
                            SearchResultType.USER -> Pages.PAGE_PROFILE
                        }.page.withParameters("id" to result.id.toString())
                        h2("display-5 link-body-emphasis mb-1") {
                            a("text-decoration-none") {
                                +result.title
                                navigates(route)
                            }
                        }
                        div("mb-2 text-secondary") {
                            +" "
                            code {
                                +result.createdAtString
                            }
                            +" "
                            linkUser(result.createdBy)
                        }
                        p { +result.descriptionShortCompiled }
                        a("badge text-decoration-none") {
                            i("bi bi-three-dots") {}
                            navigates(route)
                        }
                    }
                }
            }
            pagination(listStore.paginationStore)
        }
    }
}