package darkladyblog.darkladyblog.client.base.route

import darkladyblog.darkladyblog.client.util.decodeURI
import darkladyblog.darkladyblog.client.util.encodeURI
import darkladyblog.darkladyblog.client.util.uriQuery
import dev.fritz2.routing.Route

sealed class PageRouteBase() : Route<PageData> {

    abstract val page: Page

    override fun deserialize(hash: String): PageData =
        if (uriQuery in hash) {
            hash.split(uriQuery, limit = 2)
                .let { (name, pageData) -> Triple(name.substringBefore('#'), name.substringAfter('#'), pageData) }
                .let { (pageName, elementName, pageData) ->
                    decodeURI(pageData).let { pageData ->
                        mapOf(KEY_PAGE_NAME to pageName, KEY_ELEMENT_NAME to elementName) + pageData
                    }
                }
        } else {
            mapOf(KEY_PAGE_NAME to hash)
        }

    override fun serialize(route: PageData): String =
        (route.page ?: "") +
                route.element.let { if (it.isNullOrEmpty()) "" else "#$it" } +
                ((route - KEY_PAGE_NAME - KEY_ELEMENT_NAME).let { pageData ->
                    if (pageData.isEmpty()) "" else ("" + uriQuery + encodeURI(pageData))
                })

    companion object {
        const val KEY_PAGE_NAME: String = $$"$page"
        const val KEY_ELEMENT_NAME: String = $$"$element"
    }

}