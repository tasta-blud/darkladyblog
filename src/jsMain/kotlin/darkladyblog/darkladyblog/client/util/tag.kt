package darkladyblog.darkladyblog.client.util

import dev.fritz2.core.HtmlTag
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node

fun <E : HTMLElement> HtmlTag<E>.raw(html: String): Node =
    domNode.appendChild(document.createDocumentFragment()).also {
        it.asDynamic().innerHTML = html
    }

fun <E : HTMLElement> HtmlTag<E>.raw(content: HtmlTag<E>.() -> Unit): Node =
    domNode.appendChild(document.createTextNode("")).also {
        content()
        TODO()
    }