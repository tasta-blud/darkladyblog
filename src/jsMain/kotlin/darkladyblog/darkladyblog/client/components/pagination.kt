package darkladyblog.darkladyblog.client.components

import darkladyblog.darkladyblog.client.store.PaginationStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.Paginator
import dev.fritz2.core.Tag

fun Tag<*>.pagination(paginationStore: PaginationStore) {
    paginationStore.render(this) { paginator: Paginator ->
        nav {
            className(" justify-content-center")
            attr("aria-label", "Pagination")
            ul("pagination") {
                li("page-item") {
                    a("page-link") {
                        className(if (paginator.isFirstPage) "disabled" else "")
                        i("bi bi-chevron-double-left") {}
                        navigates(paginationStore.toFirstPage)
                    }
                }
                li("page-item") {
                    a("page-link") {
                        className(if (paginator.isFirstPage) "disabled" else "")
                        i("bi bi-chevron-compact-left") {}
                        navigates(paginationStore.toPrevPage)
                    }
                }
                li("page-item") {
                    a("page-link") {
                        className("active")
                        +"${paginator.page}"
                        navigates({ paginator.page }, paginationStore.toPage)
                    }
                }
                li("page-item") {
                    a("page-link") {
                        className(if (paginator.isLastPage) "disabled" else "")
                        i("bi bi-chevron-compact-right") {}
                        navigates(paginationStore.toNextPage)
                    }
                }
                li("page-item") {
                    a("page-link") {
                        className(if (paginator.isLastPage) "disabled" else "")
                        i("bi bi-chevron-double-right") {}
                        navigates(paginationStore.toLastPage)
                    }
                }
            }
        }
    }
}