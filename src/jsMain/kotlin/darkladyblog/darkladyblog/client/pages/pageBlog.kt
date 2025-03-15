package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.rest.RestStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.app.appBlog
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.BlogService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.model.app.BlogModel
import dev.fritz2.core.RenderContext

fun RenderContext.pageBlog(pageData: PageData) {
    div {
        val store = object : RestStore<ULong, BlogModel, BlogService>(
            BlogService,
            BlogModel.NULL_BLOG.withUserAndId(
                PrincipalStore.current?.let { UserModel.fromPrincipal(it) } ?: UserModel.NULL_USER,
                id = pageData["id"]?.toULongOrNull()
            )
        ) {
            override fun titleArgs(current: BlogModel): List<String> =
                listOf(current.title)
        }
        p("lead") {
            a("btn btn-lg btn btn-outline-light fw-bold border-white") {
                i("bi bi-arrow-return-left") {}
                navigates(Pages.PAGE_BLOGS.page.withParameters())
            }
        }
        appBlog(pageData, store)
    }
}
