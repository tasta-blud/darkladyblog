package darkladyblog.darkladyblog.client.components.controls

import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.model.nick
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Store
import kotlinx.coroutines.flow.map

fun RenderContext.linkUser(user: Store<UserModel>) {
    span {
        +"@"
        a("text-decoration-none") {
            user.map(UserModel.nick()).data.renderText()
            navigates(user.data.map { Pages.PAGE_PROFILE.page.withParameters("id" to it.id.toString()) })
        }
    }
}

fun RenderContext.linkUser(username: Store<String>, id: Store<ULong?>) {
    span {
        username.also { username ->
            +"@"
            a("text-decoration-none") {
                username.data.renderText()
                navigates(id.data.map { Pages.PAGE_PROFILE.page.withParameters("id" to it.toString()) })
            }
        }
    }
}

fun RenderContext.linkUser(user: UserModel) {
    span {
        +"@"
        a("text-decoration-none") {
            +user.nick
            navigates(Pages.PAGE_PROFILE.page.withParameters("id" to user.id.toString()))
        }
    }
}