package darkladyblog.darkladyblog.client.errorPages

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.base.route.page
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.data.PageRouter
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.i18n.ErrorTranslations
import dev.fritz2.core.RenderContext

fun RenderContext.notFoundPage(pageData: PageData) {
    div("container d-flex w-100 h-100 p-3 mx-auto flex-column") {
        div("px-3") {
            p("lead") {
                PageRouter.data.renderIf({ it.isNotEmpty() }) {
                    +ErrorTranslations.not_found_content(pageData.page ?: "")
                }
            }
            p("lead") {
                a("btn btn-lg btn btn-outline-light fw-bold border-white") {
                    +">>"
                    navigates(Pages.PAGE_DEFAULT.page.withParameters())
                }
            }
        }
    }
}
