package darkladyblog.darkladyblog.client.components.layout

import darkladyblog.darkladyblog.client.base.route.Page
import darkladyblog.darkladyblog.client.data.PageRouter.renderShownPages
import darkladyblog.darkladyblog.client.util.navigates
import dev.fritz2.core.RenderContext


fun RenderContext.layoutHeaderMenu() {
    ul("navbar-nav me-2 mb-2 mb-lg-0") {
        renderShownPages(this) { page: Page, isActive: Boolean ->
            li("nav-item") {
                a("nav-link") {
                    className(if (isActive) "active" else "")
                    attr("aria-current", "page")
                    +page.title(listOf())
                    navigates(page.withParameters())
                }
            }
        }
    }
}
