package darkladyblog.darkladyblog.client.base.route

typealias PageData = Map<String, String>

val PageData.page: String?
    get() = this[PageRouteBase.KEY_PAGE_NAME]

val PageData.element: String?
    get() = this[PageRouteBase.KEY_ELEMENT_NAME]