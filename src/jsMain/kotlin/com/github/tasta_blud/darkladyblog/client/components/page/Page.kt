package com.github.tasta_blud.darkladyblog.client.components.page

import com.github.tasta_blud.darkladyblog.client.util.routing
import io.kvision.panel.SimplePanel
import io.kvision.snabbdom.VNode

abstract class Page(
    val displayTitle: String = "",
    val route: String? = null,
    className: String? = null,
    init: (Page.() -> Unit) = {}
) : SimplePanel(className) {
    init {
        init()
    }

    override fun afterInsert(node: VNode) {
        super.afterInsert(node)
        routing.updatePageLinks()
    }

    override fun afterPatch(node: VNode) {
        super.afterPatch(node)
        routing.updatePageLinks()
    }

}
