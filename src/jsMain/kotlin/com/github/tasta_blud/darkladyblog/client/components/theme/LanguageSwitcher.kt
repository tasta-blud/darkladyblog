package com.github.tasta_blud.darkladyblog.client.components.theme

import com.github.tasta_blud.darkladyblog.client.controllers.AppController.AppScope
import io.kvision.core.Container
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.i18n.I18n
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.toolbar.buttonGroup
import io.kvision.toolbar.toolbar
import kotlinx.coroutines.launch

fun Container.languageSwitcher(vararg languages: Pair<String, String>) {
    val lang = ObservableValue(I18n.language)
    toolbar {
        buttonGroup {}.bind(lang) {
            languages.forEach { (code, label) ->
                button(
                    label,
                    when (code) {
                        "en" -> "us"
                        else -> code
                    }.let { "flag:" + it.uppercase() },
                    if (code == I18n.language)
                        ButtonStyle.OUTLINEPRIMARY
                    else
                        ButtonStyle.OUTLINESECONDARY
                ) {
                    onClick {
                        I18n.language = code
                        AppScope.launch {
                            lang.value = code
                        }
                    }
                }
            }
        }

    }
}
