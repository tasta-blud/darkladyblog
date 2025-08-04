package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.controllers.ISearchController
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.search.SearchResult
import darkladyblog.darkladyblog.common.model.app.search.SearchResult.Companion.toSearchResult
import darkladyblog.darkladyblog.server.base.Repository
import darkladyblog.darkladyblog.server.base.RepositoryService
import darkladyblog.darkladyblog.server.services.app.BlogRepositoryService
import darkladyblog.darkladyblog.server.services.app.TopicRepositoryService
import io.ktor.server.application.ApplicationCall
import org.jetbrains.exposed.dao.id.IdTable
import org.koin.core.annotation.Single
import org.jetbrains.exposed.sql.Column as EColumn
import org.jetbrains.exposed.sql.SortOrder as ESortOrder

@Single
class SearchController(
    val blogRepositoryService: BlogRepositoryService,
    val topicRepositoryService: TopicRepositoryService,
    val call: ApplicationCall
) : ISearchController {

    private fun <S : RepositoryService<TBL, ID, E, R>, TBL : IdTable<ID>, ID : Any, E : Any, R : Repository<TBL, ID, E>>
            S.mapOrder(order: Array<out Sorting>): Array<Pair<EColumn<*>, ESortOrder>> =
        order.map { (column: ColumnName, order: SortDirection) ->
            repository.alias[repository.table.columns.first { it.name == column.name }] to when (order) {
                SortDirection.ASC -> ESortOrder.ASC
                SortDirection.DESC -> ESortOrder.DESC
            }
        }.toTypedArray()

    override suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<SearchResult> =
        (blogRepositoryService.search(query, offset, limit, *blogRepositoryService.mapOrder(order)).map {
            toSearchResult(it)
        } + topicRepositoryService.search(query, offset, limit, *topicRepositoryService.mapOrder(order)).map {
            toSearchResult(it)
        }).let {
            if (offset == null || limit == null) it else it.subList(offset.toInt(), (offset + limit).toInt())
        }
}