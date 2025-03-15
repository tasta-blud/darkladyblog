package darkladyblog.darkladyblog.client

import darkladyblog.darkladyblog.client.components.layout.layout
import darkladyblog.darkladyblog.client.components.themePicker
import darkladyblog.darkladyblog.client.config.Styles
import darkladyblog.darkladyblog.client.data.PageRouter
import darkladyblog.darkladyblog.client.store.AlertMessageStore
import darkladyblog.darkladyblog.client.store.AssistantMessageStore
import darkladyblog.darkladyblog.client.store.TitleStore
import darkladyblog.darkladyblog.common.util.buildCss
import dev.fritz2.core.render
import dev.fritz2.headless.components.toastContainer
import dev.fritz2.headless.foundation.portalRoot
import kotlinx.browser.document
import kotlinx.dom.appendElement
import kotlinx.dom.appendText

fun main() {
    render {
        document.body!!.apply {
            Styles.styles.forEach { style ->
                document.head!!.appendElement("style") {
                    setAttributeNode(document.createAttribute("data-title").apply {
                        value = style.toString().replace("\n", "")
                    })
                    appendText(buildCss(style))
                }
            }
            parentElement!!.setAttributeNode(document.createAttribute("data-bs-theme").apply {
                value = "auto"
            })
            parentElement!!.classList.add("h-100")
            classList.add("h-100")
        }
        layout {
            PageRouter.data.render {
                TitleStore.data.render { title ->
                    h1("px-3 py-3") {
                        +title
                        document.title = title
                    }
                }
            }
            PageRouter.data.render {
                PageRouter.findPage(it).content(this, it)
            }
        }
        themePicker()
        toastContainer("default", "toast-container position-fixed bottom-0 end-0 mb-3 me-3 mb-5 z-3")
        portalRoot()
    }
    AlertMessageStore()
    AssistantMessageStore()
}
