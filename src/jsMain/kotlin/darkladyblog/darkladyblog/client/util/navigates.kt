package darkladyblog.darkladyblog.client.util

import darkladyblog.darkladyblog.client.base.route.PageRoute
import darkladyblog.darkladyblog.client.data.PageRouter
import dev.fritz2.core.Handler
import dev.fritz2.core.HtmlTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.w3c.dom.Element
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.events.MouseEvent

private fun <E : Element> makeHref(tag: HtmlTag<E>, href: String = "") =
    tag.attr(if (tag.domNode.tagName.equals("A", ignoreCase = true)) "href" else "data-href", "#$href")

private fun <E : Element> makeHref(tag: HtmlTag<E>, route: PageRoute) =
    makeHref(tag, route.serialize(route.default))

private fun <E : Element> makeHref(tag: HtmlTag<E>, href: Flow<String>) =
    if (tag.domNode.tagName.equals("A", ignoreCase = true))
        tag.attr("href", href.map { "#$it" })
    else
        tag.attr("data-href", href.map { "#$it" })

private fun <E : Element> makeHref(tag: HtmlTag<E>, route: Flow<PageRoute>) =
    makeHref(tag, route.map { it.serialize(it.default) })

fun HtmlTag<HTMLAnchorElement>.navigates(route: PageRoute) {
    makeHref(this, route)
    clicks handledBy { it.preventDefault() }
    clicks.map { route.default } handledBy PageRouter.navTo
}

fun HtmlTag<HTMLAnchorElement>.navigates(route: Flow<PageRoute>) {
    makeHref(this, route)
    clicks handledBy { it.preventDefault() }
    clicks.combine(route) { _, route -> route.default } handledBy PageRouter.navTo
}

fun HtmlTag<HTMLAnchorElement>.navigates(handler: Handler<Unit>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks handledBy handler
}

fun <A> HtmlTag<HTMLAnchorElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.map(transform) handledBy handler
}

fun HtmlTag<HTMLAnchorElement>.navigates(handler: Handler<Unit>, route: PageRoute) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks handledBy handler
    clicks.map { route.default } handledBy PageRouter.navTo
}

fun HtmlTag<HTMLAnchorElement>.navigates(handler: Handler<Unit>, route: Flow<PageRoute>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks handledBy handler
    clicks.combine(route) { _, route -> route.default } handledBy PageRouter.navTo
}

fun <A> HtmlTag<HTMLAnchorElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>, route: PageRoute) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.map(transform) handledBy handler
    clicks.map { route.default } handledBy PageRouter.navTo
}

fun <A> HtmlTag<HTMLAnchorElement>.navigates(
    transform: (MouseEvent) -> A,
    handler: Handler<A>,
    route: Flow<PageRoute>
) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.map(transform) handledBy handler
    clicks.combine(route) { _, route -> route.default } handledBy PageRouter.navTo
}

fun HtmlTag<HTMLButtonElement>.navigates(page: PageRoute) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.map { page.default } handledBy PageRouter.navTo
}

fun HtmlTag<HTMLButtonElement>.navigates(page: Flow<PageRoute>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.combine(page) { _, route -> route.default } handledBy PageRouter.navTo
}

fun HtmlTag<HTMLButtonElement>.navigates(handler: Handler<Unit>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks handledBy handler
}

fun <A> HtmlTag<HTMLButtonElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.map(transform) handledBy handler
}

fun HtmlTag<HTMLButtonElement>.navigates(handler: Handler<Unit>, page: PageRoute) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks handledBy handler
    clicks.map { page.default } handledBy PageRouter.navTo
}

fun HtmlTag<HTMLButtonElement>.navigates(handler: Handler<Unit>, page: Flow<PageRoute>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks handledBy handler
    clicks.combine(page) { _, route -> route.default } handledBy PageRouter.navTo
}

fun <A> HtmlTag<HTMLButtonElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>, page: PageRoute) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.map(transform) handledBy handler
    clicks.map { page.default } handledBy PageRouter.navTo
}

fun <A> HtmlTag<HTMLButtonElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>, page: Flow<PageRoute>) {
    makeHref(this, "#")
    clicks handledBy { it.preventDefault() }
    clicks.map(transform) handledBy handler
    clicks.combine(page) { _, route -> route.default } handledBy PageRouter.navTo
}
