package darkladyblog.darkladyblog.client.config

import darkladyblog.darkladyblog.client.styles.cssIndex
import darkladyblog.darkladyblog.client.styles.cssMain
import kotlinx.css.CssBuilder
import kotlin.reflect.KFunction1

object Styles {
    val styles: List<KFunction1<CssBuilder, Unit>> = listOf(
        CssBuilder::cssMain,
        CssBuilder::cssIndex,
    )
}