package darkladyblog.darkladyblog.client.components.controls

import dev.fritz2.core.HtmlTag
import dev.fritz2.core.Id
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Store
import dev.fritz2.core.placeholder
import dev.fritz2.core.type
import dev.fritz2.core.value
import dev.fritz2.core.values
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.w3c.dom.HTMLInputElement

fun RenderContext.inputEditable(
    controlStore: Store<String>,
    editingStore: Store<Boolean>,
    placeholder: String,
    type: Flow<String> = flowOf("text"),
    id: String = "editable" + controlStore.id + Id.next(),
    init: HtmlTag<HTMLInputElement>.() -> Unit = {}
) {
    editingStore.data.renderFalse {
        controlStore.data.renderText()
    }
    editingStore.data.renderTrue {
        input("form-control", id) {
            type(type)
            placeholder(placeholder)
            init()
            value(controlStore.data)
            changes.values() handledBy controlStore.update
        }
    }
}

fun RenderContext.inputEditable(
    controlStore: Store<String>,
    placeholder: String,
    type: Flow<String> = flowOf("text"),
    id: String = "editable" + controlStore.id + Id.next(),
    init: HtmlTag<HTMLInputElement>.() -> Unit = {}
) {
    input("form-control", id) {
        type(type)
        placeholder(placeholder)
        init()
        value(controlStore.data)
        changes.values() handledBy controlStore.update
    }
}