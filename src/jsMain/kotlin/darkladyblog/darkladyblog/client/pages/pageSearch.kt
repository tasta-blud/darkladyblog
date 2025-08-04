package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.app.appSearch
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.store.SearchStore
import darkladyblog.darkladyblog.client.util.navigates
import dev.fritz2.core.RenderContext

fun RenderContext.pageSearch(pageData: PageData) {
    div {
        val store = SearchStore
        p("lead") {
            a("btn btn-lg btn btn-outline-light fw-bold border-white") {
                i("bi bi-arrow-return-left") {}
                navigates(Pages.PAGE_BLOGS.page.withParameters())
            }
        }
        appSearch(pageData, store)
    }
}
