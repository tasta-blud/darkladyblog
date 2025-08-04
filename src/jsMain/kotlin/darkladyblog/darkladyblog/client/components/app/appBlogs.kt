package darkladyblog.darkladyblog.client.components.app

import darkladyblog.darkladyblog.client.base.rest.RestListStore
import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.controls.linkUser
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.BlogService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.controllers.IBlogRestController
import darkladyblog.darkladyblog.common.model.app.BlogModel
import dev.fritz2.core.RenderContext
import dev.fritz2.core.type

fun RenderContext.appBlogs(
    pageData: PageData,
    listStore: RestListStore<ULong, BlogModel, BlogService, IBlogRestController>
) {
    div {
        ul("list-group") {
            PrincipalStore.renderIfLogged(this) {
                div("btn-group") {
                    button("btn btn-primary") {
                        type("button")
                        i("bi bi-plus") {}
                        navigates(Pages.PAGE_BLOG.page.withParameters())
                    }
                }
            }
            listStore.renderEach(this) { blog ->
                li("list-group-item d-flex justify-content-between align-items-start") {
                    div("me-auto") {
                        div("fw-bold") {
                            a("text-decoration-none") {
                                +blog.title
                                navigates(Pages.PAGE_BLOG.page.withParameters("id" to blog.id.toString()))
                            }
                        }
                        div("text-secondary") {
                            +" "
                            code {
                                +blog.createdAtString
                            }
                            +" "
                            linkUser(blog.createdBy)
                        }
                        p {
                            +blog.descriptionShortCompiled
                        }
                    }
                    span("badge text-bg-primary rounded-pill") {
                        a("text-decoration-none") {
                            i("bi bi-three-dots") {}
                            navigates(Pages.PAGE_BLOG.page.withParameters("id" to blog.id.toString()))
                        }
                    }
                }
            }
            PrincipalStore.renderIfLogged(this) {
                div("btn-group") {
                    button("btn btn-primary") {
                        type("button")
                        i("bi bi-plus") {}
                        navigates(Pages.PAGE_BLOG.page.withParameters())
                    }
                }
            }
        }
    }
}