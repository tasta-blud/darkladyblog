package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.intercept.runWithToastAsync
import darkladyblog.darkladyblog.common.controllers.ISearchController
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.search.SearchResult
import dev.kilua.rpc.getService

object SearchService {
    val controller: ISearchController = getService<ISearchController>()
    suspend fun all(
        query: String,
        offset: Long? = null,
        limit: Int? = null,
        order: Array<Sorting> = arrayOf(Sorting(ColumnName("id"), SortDirection.ASC)),
    ): List<SearchResult>? =
        runWithToastAsync { controller.search(query, offset, limit, order) }.getOrNull()

    suspend fun count(query: String): Long? =
        runWithToastAsync { controller.search(query, null, null, arrayOf()).size.toLong() }.getOrNull()
}
