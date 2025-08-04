package darkladyblog.darkladyblog.client.components.layout

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.store.SearchStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.i18n.HeaderMenuTranslations
import dev.fritz2.core.Keys
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Store
import dev.fritz2.core.placeholder
import dev.fritz2.core.shortcutOf
import dev.fritz2.core.type
import dev.fritz2.core.values
import kotlinx.coroutines.flow.map

fun RenderContext.layoutHeaderSearch() {
    val query: Store<String> = RootStoreBase("")
    form("d-flex w-50 mx-auto") {
        attr("role", "search")
        div("input-group") {
            input("form-control ms-2") {
                type("search")
                placeholder(HeaderMenuTranslations.search())
                attr("aria-label", HeaderMenuTranslations.search())
                focusins {
                    target.asDynamic().select()
                }
                focuss {
                    target.asDynamic().select()
                }
                changes.values() handledBy query.update
                keydownsIf { shortcutOf(this) == Keys.Enter } handledBy {
                    it.preventDefault()
                    it.target.asDynamic().select()
                }
                navigates(
                    keydownsIf { shortcutOf(this) == Keys.Enter },
                    { _ -> query.current },
                    SearchStore.search,
                    query.data.map { Pages.PAGE_SEARCH.page.withParameters("query" to it) }
                )
            }
            button("btn btn-success") {
                type("button")
                i("bi bi-search") {}
                navigates(
                    { _ -> query.current },
                    SearchStore.search,
                    query.data.map { Pages.PAGE_SEARCH.page.withParameters("query" to it) }
                )
            }
        }
    }
}
