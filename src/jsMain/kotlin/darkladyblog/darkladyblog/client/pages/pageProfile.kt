package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.appUser
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.services.UserService
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.store.UserStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.client.util.runLater
import darkladyblog.darkladyblog.common.model.UserModel
import dev.fritz2.core.RenderContext
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

fun RenderContext.pageProfile(pageData: PageData) {
    div {
        val store = object : UserStore(UserModel.NULL_USER) {
            override fun titleArgs(current: UserModel): List<String> =
                listOf(current.nick)

            init {
                runLater {
                    PrincipalStore.data
                        .map { it?.let { UserModel.fromPrincipal(it) } }
                        .combine(data.map { pageData["id"]?.toULongOrNull()?.let { UserService.get(it) } }
                        ) { auth, req -> req ?: auth }.map { it ?: UserModel.NULL_USER } handledBy update
                    current
                }
            }
        }
        p("lead") {
            a("btn btn-lg btn btn-outline-light fw-bold border-white") {
                i("bi bi-arrow-return-left") {}
                navigates(Pages.PAGE_DEFAULT.page.withParameters())
            }
        }
        appUser(pageData, store)
    }
}
