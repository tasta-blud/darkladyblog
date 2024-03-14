package com.github.tasta_blud.darkladyblog.client.pages

import com.github.tasta_blud.darkladyblog.client.components.page.Page
import com.github.tasta_blud.darkladyblog.client.components.page.Pages
import io.kvision.html.div
import io.kvision.html.p

class Index(title: String = "", route: String? = null) : Page(title, route, "index") {
    init {
        div(className = "d-flex flex-column h-100") {
            (0..2000).forEach { i -> p { +i.toString() } }
        }
    }

}

fun Pages.index(title: String = "", route: String? = null): Index =
    Index(title, route).also { add(it, route) }
