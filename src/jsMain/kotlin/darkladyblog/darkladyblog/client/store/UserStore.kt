package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.client.services.UserService
import darkladyblog.darkladyblog.common.model.UserModel
import dev.fritz2.core.Handler
import dev.fritz2.core.Id
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Tag
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLElement

open class UserStore(initialData: UserModel, job: Job = Job(), id: String = Id.next()) :
    RootStoreBase<UserModel>(initialData, job, id) {

    val set: Handler<UserModel> = handle { current, value: UserModel ->
        UserService.save(value)
            ?: current
    }

    fun renderIfLogged(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(UserModel) -> Unit
    ): Unit =
        PrincipalStore.renderIfLogged(renderContext, into) { content(UserModel.fromPrincipal(it)) }

    fun renderIfNotLogged(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.() -> Unit
    ): Unit =
        PrincipalStore.renderIfNotLogged(renderContext, into) { content() }

    fun renderIfMe(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(UserModel) -> Unit
    ): Unit =
        renderContext.run {
            PrincipalStore.data
                .combine(data) { principal, user -> principal to user }
                .filter { (principal, user) -> principal?.username == user.username }
                .map { (_, user) -> user }
                .render(into) { content(it) }
        }

}