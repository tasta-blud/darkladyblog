package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.rest.RestListStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.app.appBlogs
import darkladyblog.darkladyblog.client.services.BlogService
import darkladyblog.darkladyblog.common.model.app.BlogModel
import dev.fritz2.core.RenderContext

fun RenderContext.pageBlogs(pageData: PageData) {
    div {
        val listStore = object : RestListStore<ULong, BlogModel, BlogService>(BlogService) {
            override fun titleArgs(current: List<BlogModel>): List<String>? =
                listOf()
        }
        appBlogs(pageData, listStore)
    }
}
