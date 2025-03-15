package darkladyblog.darkladyblog.client.base.route

class PageRouteDefault(val pages: List<Page>, val defaultPage: Page = PageRouterBase.defaultPage) : PageRouteBase() {

    override val page: Page get() = defaultPage

    override val default: PageData get() = mapOf()
}