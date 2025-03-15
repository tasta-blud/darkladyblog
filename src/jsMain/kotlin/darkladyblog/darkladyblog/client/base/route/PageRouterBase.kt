package darkladyblog.darkladyblog.client.base.route

import darkladyblog.darkladyblog.client.data.PageRouter
import darkladyblog.darkladyblog.client.store.TitleStore
import darkladyblog.darkladyblog.client.util.runLater
import dev.fritz2.core.RenderContext
import dev.fritz2.core.SimpleHandler
import dev.fritz2.core.Tag
import dev.fritz2.routing.Router
import kotlinx.browser.document
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLElement

open class PageRouterBase(val defaultRoute: PageRouteDefault, job: Job = Job()) :
    Router<PageData>(defaultRoute, job) {

    fun findPage(pageData: PageData): Page =
        (if (pageData.page.isNullOrEmpty())
            defaultRoute.pages.firstOrNull { it.page.isEmpty() }
        else
            defaultRoute.pages.firstOrNull { it.page == (pageData.page ?: "") })
            ?: defaultRoute.defaultPage

    fun createRoute(pageData: PageData = mapOf()): PageRoute =
        PageRoute(pageData)

    fun createRouteOn(pageData: PageData = mapOf()): PageRoute =
        PageRoute(current + pageData)


    fun withParameters(key: String, value: String): PageRoute =
        withParameters(mapOf(key to value))

    fun withParameters(vararg pair: Pair<String, String>): PageRoute =
        withParameters(mapOf(*pair))

    fun withParameters(pageData: PageData): PageRoute =
        PageRouter.createRoute(
            (current.page?.let { mapOf(PageRouteBase.KEY_PAGE_NAME to it) } ?: mapOf()) +
                    (current.element?.let { mapOf(PageRouteBase.KEY_ELEMENT_NAME to it) }
                        ?: mapOf()) + pageData
        )

    fun withExtraParameters(key: String, value: String): PageRoute =
        withExtraParameters(mapOf(key to value))

    fun withExtraParameters(vararg pair: Pair<String, String>): PageRoute =
        withExtraParameters(mapOf(*pair))

    fun withExtraParameters(pageData: PageData): PageRoute =
        PageRouter.createRouteOn(
            (current.page?.let { mapOf(PageRouteBase.KEY_PAGE_NAME to it) } ?: mapOf()) +
                    (current.element?.let { mapOf(PageRouteBase.KEY_ELEMENT_NAME to it) }
                        ?: mapOf()) + pageData
        )

    fun withExtraElement(element: String, pageData: PageData = mapOf()): PageRoute =
        PageRouter.createRouteOn(
            (current.page?.let { mapOf(PageRouteBase.KEY_PAGE_NAME to it) } ?: mapOf()) +
                    mapOf(PageRouteBase.KEY_ELEMENT_NAME to element) + pageData
        )

    fun withoutExtraElement(pageData: PageData = mapOf()): PageRoute =
        PageRouter.createRouteOn(
            (current.page?.let { mapOf(PageRouteBase.KEY_PAGE_NAME to it) } ?: mapOf()) +
                    pageData - PageRouteBase.KEY_ELEMENT_NAME
        )

    override val navTo: SimpleHandler<PageData>
        get() = handle { oldData, newData ->
            findPage(oldData).onHide()
            findPage(newData).let {
                it.onShow()
                TitleStore.update(it.title(listOf()))
                newData.element?.takeIf { el -> el.isNotEmpty() }?.let { el ->
                    lateinit var op: suspend () -> Unit
                    op = {
                        val element = document.querySelector(el)
                        if (element != null) element.scrollIntoView()
                        else runLater(op)
                    }
                    runLater(op)
                }
            }
            newData
        }

    fun renderShownPages(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(Page, Boolean) -> Unit
    ): Unit =
        renderContext.run {
            data.map { findPage(it) }.filter { !it.isSpecial }.render(into) {
                content(it, it == current)
            }
        }

    companion object {
        val defaultPage: Page = Page(page = "", title = { "" }, content = {})
    }
}