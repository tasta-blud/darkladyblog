package com.github.tasta_blud.darkladyblog.client.components.page

import com.github.tasta_blud.darkladyblog.client.util.routing
import io.kvision.core.Container
import io.kvision.panel.StackPanel
import io.kvision.state.ObservableValue

class Pages(
    activateLast: Boolean = true,
    className: String? = null,
    init: (Pages.() -> Unit) = {}
) : StackPanel(activateLast, className) {

    val displayTitle: ObservableValue<String> = ObservableValue("")

    init {
        init()
    }

    fun add(page: Page, route: String?) {
        if (route == null)
            super.add(page)
        else {
            super.add(page, route)
            routing.addAfterHook(route) {
                displayTitle.value = page.displayTitle
            }
        }
    }
}

fun Container.pages(
    activateLast: Boolean = true,
    className: String? = null,
    init: (Pages.() -> Unit) = {}
): Pages = Pages(activateLast, className, init).also { add(it) }
