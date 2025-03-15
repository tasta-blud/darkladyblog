package darkladyblog.darkladyblog.client.components.layout

import darkladyblog.darkladyblog.i18n.HeaderMenuTranslations
import dev.fritz2.core.RenderContext
import dev.fritz2.core.placeholder
import dev.fritz2.core.type

fun RenderContext.layoutHeaderSearch() {
    form("d-flex w-50 mx-auto") {
        attr("role", "search")
        div("input-group") {
            input("form-control ms-2") {
                type("search")
                placeholder(HeaderMenuTranslations.search())
                attr("aria-label", HeaderMenuTranslations.search())
            }
            button("btn btn-success") {
                type("button")
                i("bi bi-search") {}
            }
        }
    }
}
