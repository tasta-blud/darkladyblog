package darkladyblog.darkladyblog.client.components

import darkladyblog.darkladyblog.client.components.ThemeStore.useEffect
import darkladyblog.darkladyblog.client.store.AssistantMessageStore
import darkladyblog.darkladyblog.common.model.AssistantMessageType
import darkladyblog.darkladyblog.i18n.AssistantTranslations
import dev.fritz2.core.Keys
import dev.fritz2.core.RenderContext
import dev.fritz2.core.action
import dev.fritz2.core.height
import dev.fritz2.core.joinClasses
import dev.fritz2.core.placeholder
import dev.fritz2.core.shortcutOf
import dev.fritz2.core.src
import dev.fritz2.core.type
import dev.fritz2.core.values
import dev.fritz2.core.width
import kotlinx.browser.document

fun RenderContext.assistant() {
    div("assistant position-fixed bottom-0 start-0 mb-3 ms-3 mb-5 z-3", "assistant") {
        div("assistant-content collapse vw-100 vh-100 pe-5 py-5 my-5 bottom-0", "assistant-content") {
            form("form h-100 pe-5 pt-5 mt-5") {
                action("#")
                ul("list-group bottom-0 h-100 overflow-y-auto", "assistant-list") {
                    AssistantMessageStore.renderEach(this) { msg ->
                        li(
                            joinClasses(
                                "list-group-item", "border border-5 rounded-top-5", when (msg.type) {
                                    AssistantMessageType.QUESTION -> "border-info rounded-end-5"
                                    AssistantMessageType.ANSWER -> "border-success rounded-start-5"
                                }
                            )
                        ) {
                            +msg.content
                        }
                    }
                }
                div("input-group") {
                    input("form-control", "assistant-text") {
                        type("text")
                        placeholder(AssistantTranslations.assistant_text_placeholder())
                        attr("aria-describedby", "assistant-button")
                        focusins {
                            target.asDynamic().select()
                        }
                        focuss {
                            target.asDynamic().select()
                        }
                        changes.values() handledBy AssistantMessageStore.userInput.update
                        keyupsIf { shortcutOf(this) == Keys.Enter } handledBy {
                            it.preventDefault()
                            it.target.asDynamic().select()
                        }
                        keyupsIf { shortcutOf(this) == Keys.Enter } handledBy AssistantMessageStore.onEnter
                    }
                    button("btn btn-primary", "assistant-button") {
                        type("button")
                        i("bi bi-send") {}
                        clicks handledBy AssistantMessageStore.onEnter
                        clicks handledBy {
                            document.getElementById("assistant-text").apply {
                                asDynamic().focus()
                                asDynamic().select()
                            }
                            document.getElementById("assistant-list").apply {
                                this?.scrollTop = scrollHeight.toDouble()
                            }
                        }
                    }
                }
            }
        }
        span("d-inline-block") {
            attr("tabindex", "0")
            attr("data-bs-toggle", "popover")
            attr("data-bs-trigger", "hover focus")
            attr("data-bs-container", "body")
            attr("data-bs-content", AssistantTranslations.assistant_button_popover())
            button("btn btn-secondary") {
                type("button")
                attr("data-bs-toggle", "collapse")
                attr("data-bs-target", "#assistant-content")
                attr("aria-expanded", "false")
                attr("aria-controls", "assistant")

                img {
                    src("/favicon.png")
                    width(36)
                    height(36)
                }
            }
        }
    }
    useEffect {
        (document.getElementById("assistant-content") ?: return@useEffect).addEventListener("shown.bs.collapse", {
            document.getElementById("assistant-text").apply {
                asDynamic().focus()
                asDynamic().select()
            }
        })
        AssistantMessageStore.callback = {
            document.getElementById("assistant-list").apply {
                this?.scrollTop = scrollHeight.toDouble()
            }
        }
    }
}