package com.github.tasta_blud.darkladyblog.client.components.layout

import com.github.tasta_blud.darkladyblog.client.components.page.Pages
import com.github.tasta_blud.darkladyblog.client.components.page.pages
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.h1
import io.kvision.html.main
import io.kvision.panel.SimplePanel
import io.kvision.state.bind

class Layout(
    appTitle: String = "",
    contentBuilder: Pages.() -> Unit
) : SimplePanel("layout vh-100") {
    init {
        div(className = "d-flex flex-column h-100") {
            layoutHeader(appTitle)
            main(className = "flex-shrink-0") {
                div(className = "container px-5 mb-5") {
                    val title = h1(className = "mt-5") { +appTitle }
                    val pages = pages { contentBuilder() }
                    title.bind(pages.displayTitle) { +it }
                }
            }
            layoutFooter()
        }
    }
}

fun Container.layout(
    appTitle: String = "",
    contentBuilder: Pages.() -> Unit
): Layout =
    Layout(appTitle, contentBuilder).also { add(it) }
