package darkladyblog.darkladyblog.client.base.rest

import darkladyblog.darkladyblog.client.base.store.RootStoreListBase
import darkladyblog.darkladyblog.client.store.PaginationStore
import darkladyblog.darkladyblog.common.config.PAGE_SIZE
import darkladyblog.darkladyblog.common.model.Paginator
import dev.fritz2.core.Id
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

open class RootStorePageableBase<D>(
    limit: Int? = PAGE_SIZE,
    page: Int = 1,
    initialData: List<D> = listOf<D>(),
    job: Job = Job(),
    override val id: String = Id.next()
) : RootStoreListBase<D>(initialData, job, id) {
    val paginationStore: PaginationStore =
        PaginationStore(Paginator(initialData.size.toLong(), limit = limit, page = page))

    val pageable: Flow<List<D>> =
        data.combine(paginationStore.data) { it, p ->
            it.subList(
                p.offset.toInt(),
                p.offset.toInt() + (p.limit ?: 0)
            )
        }

    init {
        paginationStore.data.map { current } handledBy get
    }
}