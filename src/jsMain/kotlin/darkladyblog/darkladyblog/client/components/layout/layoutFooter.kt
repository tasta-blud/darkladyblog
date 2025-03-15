package darkladyblog.darkladyblog.client.components.layout

import dev.fritz2.core.HtmlTag
import dev.fritz2.core.RenderContext
import dev.fritz2.core.href
import org.w3c.dom.HTMLSpanElement


fun RenderContext.layoutFooter(contents: HtmlTag<HTMLSpanElement>.() -> Unit) {
    footer("pb-5") {
        div("fixed-bottom d-flex flex-wrap justify-content-between align-items-center py-3 border-top px-5 text-bg-secondary") {
            div("col-md-4 d-flex align-items-center") {
                a("mb-3 me-2 mb-md-0 text-body-secondary text-decoration-none lh-1") {
                    href("/")
                }
                span("mb-3 mb-md-0 text-body-secondary") {
                    contents()
                }
            }
            ul("nav col-md-4 justify-content-end list-unstyled d-flex") {
                li("ms-3") {
                    a("text-body-secondary") {
                        href("#")
                    }
                }
                li("ms-3") {
                    a("text-body-secondary") {
                        href("#")
                    }
                }
                li("ms-3") {
                    a("text-body-secondary") {
                        href("#")
                    }
                }
            }
        }
    }
}
