package darkladyblog.darkladyblog.client.base.route

import darkladyblog.darkladyblog.client.data.PageRouter

class PageRoute(override val default: PageData) : PageRouteBase() {
    override val page: Page get() = PageRouter.findPage(default)
}