package darkladyblog.darkladyblog.client.data

import darkladyblog.darkladyblog.client.base.route.PageRouteDefault
import darkladyblog.darkladyblog.client.base.route.PageRouterBase
import darkladyblog.darkladyblog.client.config.Pages

object PageRouter : PageRouterBase(PageRouteDefault(Pages.entries.map { it.page }, Pages.PAGE_NOT_FOUND.page))