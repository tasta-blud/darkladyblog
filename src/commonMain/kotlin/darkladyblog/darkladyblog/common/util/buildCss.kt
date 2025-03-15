package darkladyblog.darkladyblog.common.util

import kotlinx.css.CssBuilder

fun buildCss(builder: CssBuilder.() -> Unit): String =
    CssBuilder().apply(builder).toString().trim()
