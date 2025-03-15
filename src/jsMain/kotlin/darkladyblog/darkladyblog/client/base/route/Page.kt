package darkladyblog.darkladyblog.client.base.route

import darkladyblog.darkladyblog.client.data.PageRouter
import dev.fritz2.core.Lenses
import dev.fritz2.core.RenderContext

@Lenses
data class Page(
    val page: String,
    val element: String = "",
    val title: (args: List<String>) -> String,
    val isSpecial: Boolean = false,
    val onShow: () -> Unit = {},
    val onHide: () -> Unit = {},
    val content: RenderContext.(PageData) -> Unit
) {
    fun withParameters(key: String, value: String): PageRoute =
        withParameters(mapOf(key to value))

    fun withParameters(vararg pair: Pair<String, String>): PageRoute =
        withParameters(mapOf(*pair))

    fun withParameters(pageData: PageData): PageRoute =
        PageRouter.createRoute(
            mapOf(
                PageRouteBase.KEY_PAGE_NAME to page,
                PageRouteBase.KEY_ELEMENT_NAME to element
            ) + pageData
        )

    fun withExtraParameters(key: String, value: String): PageRoute =
        withExtraParameters(mapOf(key to value))

    fun withExtraParameters(vararg pair: Pair<String, String>): PageRoute =
        withExtraParameters(mapOf(*pair))

    fun withExtraParameters(pageData: PageData): PageRoute =
        PageRouter.createRouteOn(
            mapOf(
                PageRouteBase.KEY_PAGE_NAME to page,
                PageRouteBase.KEY_ELEMENT_NAME to element
            ) + pageData
        )

    fun withExtraElement(element: String, pageData: PageData = mapOf()): PageRoute =
        PageRouter.createRouteOn(
            mapOf(
                PageRouteBase.KEY_PAGE_NAME to page,
                PageRouteBase.KEY_ELEMENT_NAME to element
            ) + pageData
        )

    fun withoutExtraElement(element: String, pageData: PageData = mapOf()): PageRoute =
        PageRouter.createRouteOn(
            mapOf(
                PageRouteBase.KEY_PAGE_NAME to page
            ) + pageData - PageRouteBase.KEY_ELEMENT_NAME
        )

    companion object
}