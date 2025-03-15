package darkladyblog.darkladyblog.client.base.rest

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.common.base.IdModel
import dev.fritz2.core.Handler
import dev.fritz2.core.Id
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Tag
import kotlinx.coroutines.Job
import org.w3c.dom.HTMLElement

open class RestStore<ID : Any, M : IdModel<ID>, R : RestService<ID, M>>(
    val restService: R,
    initialData: M,
    job: Job = Job(),
    override val id: String = Id.next()
) : RootStoreBase<M>(initialData, job, id) {

    val init: Handler<M> = handle { current, value: M ->
        current
            .takeIf { it == initialData }
            ?.takeIf { it.id != null }
            ?.let { restService.get(it.id!!) }
            ?: current
    }

    val get: Handler<M> = handle { current, value: M ->
        current
            .takeIf { it.id != null }
            ?.let { restService.get(it.id!!) }
            ?: current
    }

    val set: Handler<M> = handle { current, value: M ->
        restService.save(value)
            ?: current
    }

    val delete: Handler<M> = handle { current, value: M ->
        value.id?.let { restService.delete(it) }
        current
    }

    fun renderIfExists(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(M) -> Unit
    ): Unit =
        renderIf(renderContext, { it.id != null }, into, content)

    fun renderIfNew(renderContext: RenderContext, into: Tag<HTMLElement>? = null, content: Tag<*>.(M) -> Unit): Unit =
        renderIf(renderContext, { it.id == null }, into, content)

    init {
        data handledBy init
    }
}