package darkladyblog.darkladyblog.client.components.layout

import darkladyblog.darkladyblog.client.components.assistant
import darkladyblog.darkladyblog.i18n.ApplicationTranslations
import dev.fritz2.core.HtmlTag
import dev.fritz2.core.RenderContext
import org.w3c.dom.HTMLElement

fun RenderContext.layout(content: HtmlTag<HTMLElement>.() -> Unit) {
    div("d-flex flex-column h-100") {
        layoutHeader {
        }
        main("container") {
            content()
        }
        layoutFooter {
            assistant()
            +ApplicationTranslations.application_copyright()
        }
    }
}
