package com.github.tasta_blud.darkladyblog.client.components.layout

import com.github.tasta_blud.darkladyblog.client.components.theme.iconThemeSwitcher
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.footer
import io.kvision.html.link
import io.kvision.html.span
import io.kvision.panel.SimplePanel

class LayoutFooter : SimplePanel("layout-footer") {
    init {
        footer(className = "footer fixed-bottom mt-auto px-5 py-3 bg-body-tertiary") {
            div(className = "container text-center") {
                span(className = "text-body-secondary border-top") {
                    span(rich = true) { +"&copy; 2024" }
                    span(rich = true) { +"&nbsp;" }
                    link(label = "Tasta Blud", target = "_blank", url = "https://github.com/tasta-blud")
                }
            }
            iconThemeSwitcher()
        }
    }
}

fun Container.layoutFooter(): LayoutFooter =
    LayoutFooter().also { add(it) }
