package darkladyblog.darkladyblog.client.util

import darkladyblog.darkladyblog.client.base.route.PageRoute
import darkladyblog.darkladyblog.client.data.PageRouter
import dev.fritz2.core.Handler
import dev.fritz2.core.HtmlTag
import dev.fritz2.core.Listener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.w3c.dom.Element
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.UIEvent

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

fun <E : UIEvent, EL : HTMLElement> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    route: PageRoute
) {
    makeHref(this, route)
    listener handledBy { it.preventDefault() }
    listener.map { route.default } handledBy PageRouter.navTo
}

fun <E : UIEvent, EL : HTMLElement> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    route: Flow<PageRoute>
) {
    makeHref(this, route)
    listener handledBy { it.preventDefault() }
    listener.combine(route) { _, route -> route.default } handledBy PageRouter.navTo
}

fun <E : UIEvent, EL : HTMLElement> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    handler: Handler<Unit>
) {
    makeHref(this, "#")
    listener handledBy { it.preventDefault() }
    listener handledBy handler
}

fun <E : UIEvent, EL : HTMLElement, A> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    transform: (E) -> A,
    handler: Handler<A>
) {
    makeHref(this, "#")
    listener handledBy { it.preventDefault() }
    listener.map(transform) handledBy handler
}

fun <E : UIEvent, EL : HTMLElement> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    handler: Handler<Unit>,
    route: PageRoute
) {
    makeHref(this, "#")
    listener handledBy { it.preventDefault() }
    listener handledBy handler
    listener.map { route.default } handledBy PageRouter.navTo
}

fun <E : UIEvent, EL : HTMLElement> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    handler: Handler<Unit>,
    route: Flow<PageRoute>
) {
    makeHref(this, "#")
    listener handledBy { it.preventDefault() }
    listener handledBy handler
    listener.combine(route) { _, route -> route.default } handledBy PageRouter.navTo
}

fun <E : UIEvent, EL : HTMLElement, A> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    transform: (E) -> A,
    handler: Handler<A>,
    route: PageRoute
) {
    makeHref(this, "#")
    listener handledBy { it.preventDefault() }
    listener.map(transform) handledBy handler
    listener.map { route.default } handledBy PageRouter.navTo
}

fun <E : UIEvent, EL : HTMLElement, A> HtmlTag<EL>.navigates(
    listener: Listener<E, EL>,
    transform: (E) -> A,
    handler: Handler<A>,
    route: Flow<PageRoute>
) {
    makeHref(this, "#")
    listener handledBy { it.preventDefault() }
    listener.map(transform) handledBy handler
    listener.combine(route) { _, route -> route.default } handledBy PageRouter.navTo
}


fun HtmlTag<HTMLAnchorElement>.navigates(route: PageRoute): Unit =
    navigates(clicks, route)

fun HtmlTag<HTMLAnchorElement>.navigates(route: Flow<PageRoute>): Unit =
    navigates(clicks, route)

fun HtmlTag<HTMLAnchorElement>.navigates(handler: Handler<Unit>): Unit =
    navigates(clicks, handler)

fun <A> HtmlTag<HTMLAnchorElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>): Unit =
    navigates(clicks, transform, handler)

fun HtmlTag<HTMLAnchorElement>.navigates(handler: Handler<Unit>, route: PageRoute): Unit =
    navigates(clicks, handler, route)

fun HtmlTag<HTMLAnchorElement>.navigates(handler: Handler<Unit>, route: Flow<PageRoute>): Unit =
    navigates(clicks, handler, route)

fun <A> HtmlTag<HTMLAnchorElement>.navigates(
    transform: (MouseEvent) -> A,
    handler: Handler<A>,
    route: PageRoute
): Unit =
    navigates(clicks, transform, handler, route)

fun <A> HtmlTag<HTMLAnchorElement>.navigates(
    transform: (MouseEvent) -> A,
    handler: Handler<A>,
    route: Flow<PageRoute>
): Unit =
    navigates(clicks, transform, handler, route)

fun HtmlTag<HTMLButtonElement>.navigates(page: PageRoute): Unit =
    navigates(clicks, page)

fun HtmlTag<HTMLButtonElement>.navigates(page: Flow<PageRoute>): Unit =
    navigates(clicks, page)

fun HtmlTag<HTMLButtonElement>.navigates(handler: Handler<Unit>): Unit =
    navigates(clicks, handler)

fun <A> HtmlTag<HTMLButtonElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>): Unit =
    navigates(clicks, transform, handler)

fun HtmlTag<HTMLButtonElement>.navigates(handler: Handler<Unit>, page: PageRoute): Unit =
    navigates(clicks, handler, page)

fun HtmlTag<HTMLButtonElement>.navigates(handler: Handler<Unit>, page: Flow<PageRoute>): Unit =
    navigates(clicks, handler, page)

fun <A> HtmlTag<HTMLButtonElement>.navigates(transform: (MouseEvent) -> A, handler: Handler<A>, page: PageRoute): Unit =
    navigates(clicks, transform, handler, page)

fun <A> HtmlTag<HTMLButtonElement>.navigates(
    transform: (MouseEvent) -> A,
    handler: Handler<A>,
    page: Flow<PageRoute>
): Unit =
    navigates(clicks, transform, handler, page)
