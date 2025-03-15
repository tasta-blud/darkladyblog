package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.common.model.Paginator
import dev.fritz2.core.Handler
import dev.fritz2.core.Id
import kotlinx.coroutines.Job
import kotlin.math.max
import kotlin.math.min

class PaginationStore(initialData: Paginator, job: Job = Job(), override val id: String = Id.next()) :
    RootStoreBase<Paginator>(initialData, job, id) {

    val resize: Handler<Long> = handle { it: Paginator, count: Long ->
        it.copy(count = count)
    }

    val toFirstPage: Handler<Unit> = handle {
        it.copy(page = 1)
    }

    val toLastPage: Handler<Unit> = handle {
        it.copy(page = it.pages)
    }

    val toPrevPage: Handler<Unit> = handle {
        it.copy(page = max(1, it.page - 1))
    }

    val toNextPage: Handler<Unit> = handle {
        it.copy(page = min(it.pages, it.page + 1))
    }

    val toPage: Handler<Int> = handle { it: Paginator, page ->
        it.copy(page = page)
    }
}