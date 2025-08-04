package darkladyblog.darkladyblog.common.controllers

import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.search.SearchResult
import dev.kilua.rpc.annotations.Method
import dev.kilua.rpc.annotations.RpcBinding
import dev.kilua.rpc.annotations.RpcService
import org.koin.core.component.KoinComponent

@RpcService
interface ISearchController : KoinComponent {
    @RpcBinding(Method.POST, "search")
    suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<SearchResult>
}