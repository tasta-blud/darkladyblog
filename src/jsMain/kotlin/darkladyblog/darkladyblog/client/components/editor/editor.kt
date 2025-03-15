package darkladyblog.darkladyblog.client.components.editor

import darkladyblog.darkladyblog.client.util.css
import darkladyblog.darkladyblog.client.util.htmls
import darkladyblog.darkladyblog.client.util.jsCreate
import darkladyblog.darkladyblog.client.util.runLaterTimed
import darkladyblog.darkladyblog.i18n.CommonTranslations
import dev.fritz2.core.HtmlTag
import dev.fritz2.core.Id
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Store
import dev.fritz2.core.placeholder
import dev.fritz2.core.value
import dev.fritz2.core.values
import kotlinx.browser.document
import kotlinx.css.minHeight
import kotlinx.css.vh
import kotlinx.css.zIndex
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLTextAreaElement

fun RenderContext.textareaEditor(
    source: Store<String>,
    compiled: Store<String>,
    id: String = "editormd-" + Id.next(),
    textareaId: String = "item-source-$id",
    divId: String = "item-source-div-$id",
    initDiv: HtmlTag<HTMLDivElement>.() -> Unit = {},
    initTextarea: HtmlTag<HTMLTextAreaElement>.() -> Unit = {},
) {
    div("editormd form-control", divId) {
        +CommonTranslations.description()
        css {
            minHeight = 50.vh
            zIndex = 10000
        }
        initDiv()
        textarea("form-control", textareaId) {
            placeholder(CommonTranslations.description())
            initTextarea()
            value(source.data)
            changes.values() handledBy source.update
        }
        runLaterTimed { editormd(divId, mdOptions.jsCreate { markdown = source.current }) }
        source.data handledBy { document.getElementById(textareaId)!!.textContent = it }
        changes.htmls() handledBy compiled.update
    }
}