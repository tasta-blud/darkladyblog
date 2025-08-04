package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.rest.RootStorePageableBase
import darkladyblog.darkladyblog.client.intercept.runWithToast
import darkladyblog.darkladyblog.client.services.SearchService
import darkladyblog.darkladyblog.common.model.app.search.SearchResult
import dev.fritz2.core.Handler

object SearchStore : RootStorePageableBase<SearchResult>() {

    val search: Handler<String> = handle { state, query ->
        runWithToast { SearchService.all(query) }.map { it!! }.getOrDefault(state)
    }

    override fun titleArgs(current: List<SearchResult>): List<String>? =
        listOf()
}