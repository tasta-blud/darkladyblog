package darkladyblog.darkladyblog.client.components

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import dev.fritz2.core.Handler
import dev.fritz2.core.RenderContext
import dev.fritz2.core.type
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.w3c.dom.MediaQueryList


private object Themes {

    val themeIcons = arrayOf(
        "auto" to "circle-half",
        "light" to "sun-fill",
        "dark" to "moon-stars-fill"
    )

    fun getStoredTheme(): String? =
        localStorage.getItem("theme")

    fun setStoredTheme(theme: String) {
        localStorage.setItem("theme", theme)
    }

    private fun isPreferredThemeDark(): Boolean =
        queryPreferredTheme().matches

    fun getPreferredTheme(): String =
        getStoredTheme() ?: if (isPreferredThemeDark()) "dark" else "light"

    fun setTheme(theme: String) {
        (document.documentElement ?: return).setAttribute(
            "data-bs-theme", when {
                theme != "auto" -> theme
                else -> getPreferredTheme()
            }
        )
    }

    fun queryPreferredTheme(): MediaQueryList =
        window.matchMedia("(prefers-color-scheme: dark)")

}

object ThemeStore : RootStoreBase<String>(Themes.getPreferredTheme()) {
    val toggle: Handler<String> = handleAndEmit<String, String> { _, newTheme ->
        Themes.setStoredTheme(newTheme)
        Themes.setTheme(newTheme)
        newTheme
    }
}

fun RenderContext.themePicker() {
    val size = "1em"
    div("theme-picker position-fixed bottom-0 end-0 mb-3 me-3 mb-5 z-3") {
        div("dropdown bd-mode-toggle") {
            button("btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center") {
                type("button")
                attr("data-bs-toggle", "dropdown")
                ThemeStore.render(this) { currentTheme ->
                    val icon = Themes.themeIcons.first { it.first == currentTheme }.second
                    i("bi bi-$icon my-1 theme-icon-active") {
                        attr("width", size)
                        attr("height", size)
                    }
                }
            }
            ul("dropdown-menu dropdown-menu-end shadow", "theme-dropdown") {
                Themes.themeIcons.forEach { (themeName, icon) ->
                    li {
                        button {
                            className(ThemeStore.data, "") {
                                "dropdown-item d-flex align-items-center" +
                                        if (it == themeName) " active" else ""
                            }
                            clicks handledBy { ThemeStore.toggle(themeName) }
                            i("bi bi-$icon me-2 opacity-50") {
                                attr("width", size)
                                attr("height", size)
                            }
                            +themeName.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                            ThemeStore.render(this) {
                                if (it == themeName) {
                                    i("bi bi-check2 ms-auto") {
                                        attr("width", size)
                                        attr("height", size)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Themes.setTheme(Themes.getPreferredTheme())
    Themes.queryPreferredTheme().addEventListener("change", {
        if (Themes.getStoredTheme() == null) Themes.setTheme(Themes.getPreferredTheme())
    })

}
