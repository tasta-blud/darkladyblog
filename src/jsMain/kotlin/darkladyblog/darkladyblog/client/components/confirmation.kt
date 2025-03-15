package darkladyblog.darkladyblog.client.components

import darkladyblog.darkladyblog.client.base.route.PageRoute
import darkladyblog.darkladyblog.client.data.PageRouter
import darkladyblog.darkladyblog.client.util.navigates
import dev.fritz2.core.Handler
import dev.fritz2.core.HtmlTag
import dev.fritz2.core.Id
import dev.fritz2.core.Listener
import dev.fritz2.core.RenderContext
import dev.fritz2.core.id
import dev.fritz2.core.tabIndex
import dev.fritz2.core.type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent

fun <A> RenderContext.confirm(
    transform: Flow<A>,
    handler: Handler<A>,
    btnClass: Flow<String> = flowOf("primary"),
    modalTitle: Flow<String> = flowOf(""),
    modalId: String = "confirmModal" + Id.next(),
    modalLabelId: String = "${modalId}Label",
    route: Flow<PageRoute?>? = null,
    buttonContent: HtmlTag<HTMLButtonElement>.() -> Unit,
    dialogContent: HtmlTag<HTMLElement>.() -> Unit,
) {
    this.confirm<A>(
        { clicks, el ->
            clicks.combine(transform) { mouseEvent, model -> model } handledBy handler
            if (route != null) el.apply {
                navigates(route.map { it ?: PageRouter.withoutExtraElement() })
            }
        },
        btnClass,
        modalTitle,
        modalId,
        modalLabelId,
        buttonContent,
        dialogContent
    )
}

fun <A> RenderContext.confirm(
    transform: (MouseEvent) -> A,
    handler: Handler<A>,
    btnClass: Flow<String> = flowOf("primary"),
    modalTitle: Flow<String> = flowOf(""),
    modalId: String = "confirmModal" + Id.next(),
    modalLabelId: String = "${modalId}Label",
    route: Flow<PageRoute?>? = null,
    buttonContent: HtmlTag<HTMLButtonElement>.() -> Unit,
    dialogContent: HtmlTag<HTMLElement>.() -> Unit,
) {
    this.confirm<A>(
        { clicks, el ->
            clicks.map(transform) handledBy handler
            if (route != null) el.apply {
                navigates(route.map { it ?: PageRouter.withoutExtraElement() })
            }
        },
        btnClass,
        modalTitle,
        modalId,
        modalLabelId,
        buttonContent,
        dialogContent
    )
}

private fun <A> RenderContext.confirm(
    op: RenderContext.(Listener<MouseEvent, HTMLButtonElement>, HtmlTag<HTMLButtonElement>) -> Unit,
    btnClass: Flow<String> = flowOf("primary"),
    modalTitle: Flow<String> = flowOf(""),
    modalId: String = "confirmModal" + Id.next(),
    modalLabelId: String = "${modalId}Label",
    buttonContent: HtmlTag<HTMLButtonElement>.() -> Unit,
    dialogContent: HtmlTag<HTMLElement>.() -> Unit,
) {
    div("modal fade") {
        id(modalId)
        tabIndex(-1)
        attr("aria-labelledby", modalLabelId)
        attr("aria-hidden", "true")
        div("modal-dialog") {
            div("modal-content") {
                div("modal-header") {
                    h1("modal-title fs-5") {
                        id(modalLabelId)
                        modalTitle.renderText()
                    }
                    button("btn-close") {
                        type("button")
                        attr("data-bs-dismiss", "modal")
                        attr("aria-label", "Close")
                    }
                }
                div("modal-body") { dialogContent() }
                div("modal-footer") {
                    button("btn btn-secondary") {
                        type("button")
                        attr("data-bs-dismiss", "modal")
                        i("bi bi-x") {}
                    }
                    button("btn") {
                        className(btnClass.map { "btn-$it" })
                        type("button")
                        attr("data-bs-dismiss", "modal")
                        i("bi bi-hand-thumbs-up") {}
                        this@confirm.op(clicks, this)
                    }
                }
            }
        }
    }
    button("btn") {
        className(btnClass.map { "btn-$it" })
        type("button")
        attr("data-bs-toggle", "modal")
        attr("data-bs-target", "#$modalId")
        buttonContent()
    }
}
