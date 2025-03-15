package darkladyblog.darkladyblog.client.pages

import darkladyblog.darkladyblog.client.base.route.PageData
import darkladyblog.darkladyblog.client.components.appRegistration
import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.store.RegistrationStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.Registration
import dev.fritz2.core.RenderContext

fun RenderContext.pageRegistration(pageData: PageData) {
    div {
        val store = object : RegistrationStore(Registration.NULL_REGISTRATION) {
            override fun titleArgs(current: Registration): List<String> =
                listOf(current.nick)
        }
        p("lead") {
            a("btn btn-lg btn btn-outline-light fw-bold border-white") {
                i("bi bi-arrow-return-left") {}
                navigates(Pages.PAGE_DEFAULT.page.withParameters())
            }
        }
        appRegistration(pageData, store)
    }
}
