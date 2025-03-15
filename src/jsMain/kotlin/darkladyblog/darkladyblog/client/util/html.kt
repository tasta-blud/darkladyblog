package darkladyblog.darkladyblog.client.util

import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLScriptElement
import org.w3c.dom.HTMLStyleElement


@Suppress("UNCHECKED_CAST")
fun <E : HTMLElement, C : HTMLElement> E.createElement(tagName: String, attrs: Map<String, String>): C =
    appendElement(tagName) {
        attrs.forEach {
            setAttributeNode(document.createAttribute(it.key).apply { value = it.value })
        }
    } as C

fun <E : HTMLElement, C : HTMLElement> E.createElement(tagName: String, vararg attrs: Pair<String, String>): C =
    createElement(tagName, mapOf(*attrs))

fun appendStyleHref(href: String): HTMLStyleElement =
    document.head!!.createElement("style", "href" to href)

fun appendScriptSrc(src: String): HTMLScriptElement =
    document.head!!.createElement("script", "src" to src)
