package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.i18n.ApplicationTranslations
import dev.fritz2.core.RenderContext

fun RenderContext.pageIndex(pageData: PageData) {
    div("top-0 w-100 h-100 pt-5 mt-5 px-0 mx-0") {
        div("top-50 w-100 h-100 align-middle") {
            a("stretched-link nav-link text-center fw-bold fs-1") {
                h1 {
                    +ApplicationTranslations.application_title()
                }
                p {
                    i("bi bi-sign-do-not-enter") {}
                }
                navigates(Pages.PAGE_BLOGS.page.withParameters())
            }
        }
    }
}
