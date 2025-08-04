package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.common.base.Modified
import darkladyblog.darkladyblog.common.base.Owned
import darkladyblog.darkladyblog.common.model.Principal
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Store
import dev.fritz2.core.Tag
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLElement

object PrincipalStore : RootStoreBase<Principal?>(null) {
    init {
        initialize {
            data.filter { it == null }.map { } handledBy LoginStore.tryLogin
        }
    }

    fun renderIfLogged(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(Principal) -> Unit
    ): Unit =
        renderIf(renderContext, { it != null }, into) { it?.let { content(it) } }

    fun renderIfNotLogged(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.() -> Unit
    ): Unit =
        renderIf(renderContext, { it == null }, into) { content() }

    fun <M : Owned<*>> renderIfMy(
        renderContext: RenderContext,
        store: Store<M>,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(Principal, M) -> Unit
    ): Unit =
        renderContext.run {
            data
                .combine(store.data) { principal, model -> principal to model }
                .renderIf(
                    { (principal, owned) -> principal?.username == owned.createdBy.username },
                    into
                ) { (principal, owned) ->
                    principal?.let { content(it, owned) }
                }
        }

    fun <M : Modified<*>> renderIfMy(
        renderContext: RenderContext,
        store: Store<M>,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(Principal, M) -> Unit
    ): Unit =
        renderContext.run {
            data.combine(store.data) { principal, model -> principal to model }
                .renderIf(
                    { (principal, owned) ->
                        principal?.username == owned.createdBy.username || principal?.username == owned.updatedBy?.username
                    },
                    into
                ) { (principal, owned) ->
                    principal?.let { content(it, owned) }
                }
        }
}
