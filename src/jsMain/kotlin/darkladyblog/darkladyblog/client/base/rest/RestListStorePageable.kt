package darkladyblog.darkladyblog.client.base.rest

import darkladyblog.darkladyblog.client.store.PaginationStore
import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.base.IdModel
import darkladyblog.darkladyblog.common.config.PAGE_SIZE
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.Paginator
import dev.fritz2.core.Id
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map

open class RestListStorePageable<ID : Any, M : IdModel<ID>, R : RestService<ID, M, C>, C : IRestController<ID, M>>(
    restService: R,
    limit: Int? = PAGE_SIZE,
    page: Int = 1,
    initialData: List<M> = listOf<M>(),
    order: Array<Sorting> = arrayOf(Sorting(ColumnName("id"), SortDirection.ASC)),
    job: Job = Job(),
    override val id: String = Id.next()
) : RestListStore<ID, M, R, C>(restService, initialData, order = order, job, id) {

    val paginationStore: PaginationStore =
        PaginationStore(Paginator(initialData.size.toLong(), limit = limit, page = page))

    override suspend fun countIt(): Long =
        restService.count() ?: 0

    override suspend fun all(): List<M>? =
        restService.all(paginationStore.current.offset, paginationStore.current.limit)

    init {
        data.map { countIt() } handledBy paginationStore.resize
        paginationStore.data.map { current } handledBy get
    }
}