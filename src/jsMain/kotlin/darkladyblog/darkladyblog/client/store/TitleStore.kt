package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.client.data.PageRouter

object TitleStore : RootStoreBase<String>(
    PageRouter.findPage(PageRouter.current).let { it.title(listOf(it.page)) }, id = "title"
)