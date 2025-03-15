package darkladyblog.darkladyblog.client.components.layout

import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.i18n.ApplicationTranslations
import dev.fritz2.core.HtmlTag
import dev.fritz2.core.RenderContext
import dev.fritz2.core.height
import dev.fritz2.core.id
import dev.fritz2.core.src
import dev.fritz2.core.type
import dev.fritz2.core.width
import org.w3c.dom.HTMLDivElement

fun RenderContext.layoutHeader(contents: HtmlTag<HTMLDivElement>.() -> Unit) {
    header {
        nav("navbar navbar-expand-lg fixed-top px-5 text-bg-secondary") {
            a("navbar-brand mb-2") {
                img {
                    src("/favicon.png")
                    width(20)
                    height(20)
                }
                +ApplicationTranslations.application_title()
                navigates(Pages.PAGE_DEFAULT.page.withParameters())
            }
            button("navbar-toggler") {
                type("button")
                attr("data-bs-toggle", "collapse")
                attr("data-bs-target", "#navbarSupportedContent")
                attr("aria-controls", "navbarSupportedContent")
                attr("aria-expanded", "false")
                attr("aria-label", "Toggle navigation")
                span("navbar-toggler-icon") {
                }
            }
            div("collapse navbar-collapse") {
                id("navbarSupportedContent")
                layoutHeaderMenu()
                layoutHeaderSearch()
                contents()
                layoutHeaderUser()
            }
        }
        div("pb-5") {}
    }
}
