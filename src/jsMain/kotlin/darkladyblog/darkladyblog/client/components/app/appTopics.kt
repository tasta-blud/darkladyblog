package darkladyblog.darkladyblog.client.components.app

import darkladyblog.darkladyblog.client.base.rest.RestListStorePageable
import darkladyblog.darkladyblog.client.base.rest.RestStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.controls.linkUser
import darkladyblog.darkladyblog.client.components.pagination
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.BlogService
import darkladyblog.darkladyblog.client.services.TopicService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.controllers.IBlogRestController
import darkladyblog.darkladyblog.common.controllers.ITopicRestController
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.model.app.TopicModel
import dev.fritz2.core.RenderContext
import dev.fritz2.core.type


fun RenderContext.appTopics(pageData: PageData, store: RestStore<ULong, BlogModel, BlogService, IBlogRestController>) {
    div {
        store.render(this) {
            val listStore = object : RestListStorePageable<ULong, TopicModel, TopicService, ITopicRestController>(
                TopicService,
                order = arrayOf(
                    Sorting(ColumnName("updated_at"), SortDirection.DESC),
                    Sorting(ColumnName("created_at"), SortDirection.DESC),
                    Sorting(ColumnName("id"), SortDirection.DESC),
                )
            ) {
                override suspend fun countIt(): Long =
                    store.current.id.let { id ->
                        if (id != null)
                            restService.count(id)
                        else
                            restService.count()
                    } ?: 0

                override suspend fun all(): List<TopicModel>? =
                    store.current.id.let { id ->
                        if (id != null)
                            restService.all(
                                id,
                                paginationStore.current.offset,
                                paginationStore.current.limit,
                                order = order
                            )
                        else
                            restService.all(
                                paginationStore.current.offset,
                                paginationStore.current.limit,
                                order = order
                            )
                    }
            }
            pagination(listStore.paginationStore)
            div("div") {
                PrincipalStore.renderIfLogged(this) {
                    div("btn-group") {
                        button("btn btn-primary") {
                            type("button")
                            i("bi bi-plus") {}
                            navigates(Pages.PAGE_TOPIC.page.withParameters())
                        }
                    }
                }
                listStore.renderEach(this) { topic ->
                    article("mb-4") {
                        h2("display-5 link-body-emphasis mb-1") {
                            a("text-decoration-none") {
                                +topic.title
                                navigates(Pages.PAGE_TOPIC.page.withParameters("id" to topic.id.toString()))
                            }
                        }
                        div("mb-2 text-secondary") {
                            +" "
                            code {
                                +topic.createdAtString
                            }
                            +" "
                            linkUser(topic.createdBy)
                        }
                        p { +topic.descriptionShortCompiled }
                        a("badge text-decoration-none") {
                            i("bi bi-three-dots") {}
                            navigates(Pages.PAGE_TOPIC.page.withParameters("id" to topic.id.toString()))
                        }
                    }
                }
            }
            pagination(listStore.paginationStore)
        }
    }
}

