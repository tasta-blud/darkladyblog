package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.rest.RestStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.app.appTopic
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.TopicService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.model.app.TopicModel
import dev.fritz2.core.RenderContext
import kotlinx.coroutines.flow.map

fun RenderContext.pageTopic(pageData: PageData) {
    div {
        val store = object : RestStore<ULong, TopicModel, TopicService>(
            TopicService,
            TopicModel.NULL_TOPIC.withBlogAndUserAndId(
                BlogModel.NULL_BLOG.withUserAndId(
                    PrincipalStore.current?.let { UserModel.fromPrincipal(it) } ?: UserModel.NULL_USER, null),
                PrincipalStore.current?.let { UserModel.fromPrincipal(it) } ?: UserModel.NULL_USER,
                id = pageData["id"]?.toULongOrNull()
            )
        ) {
            override fun titleArgs(current: TopicModel): List<String> =
                listOf(current.title)
        }
        p("lead") {
            a("btn btn-lg btn btn-outline-light fw-bold border-white") {
                i("bi bi-arrow-return-left") {}
                navigates(store.data.map { Pages.PAGE_BLOG.page.withParameters("id" to it.blog.id.toString()) })
            }
        }
        appTopic(pageData, store)
    }
}
