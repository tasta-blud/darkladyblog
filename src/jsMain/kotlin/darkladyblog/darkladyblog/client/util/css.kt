package darkladyblog.darkladyblog.client.util

import darkladyblog.darkladyblog.common.util.buildCss
import dev.fritz2.core.HtmlTag
import kotlinx.css.CssBuilder
import org.w3c.dom.Element

@CssMarker
fun <E : Element> HtmlTag<E>.css(@CssMarker builder: CssBuilder.() -> Unit) {
    inlineStyle(buildCss(builder))
}
