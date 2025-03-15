package darkladyblog.darkladyblog.client.base.rest

import darkladyblog.darkladyblog.client.base.store.RootStoreListBase
import darkladyblog.darkladyblog.common.base.IdModel
import dev.fritz2.core.Handler
import dev.fritz2.core.Id
import dev.fritz2.core.IdProvider
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Tag
import kotlinx.coroutines.Job
import org.w3c.dom.HTMLElement

open class RestListStore<ID : Any, M : IdModel<ID>, R : RestService<ID, M>>(
    val restService: R,
    initialData: List<M> = listOf<M>(),
    job: Job = Job(),
    override val id: String = Id.next()
) : RootStoreListBase<M>(initialData, job, id) {

    override suspend fun countIt(): Long =
        restService.count() ?: 0

    override val init: Handler<List<M>> = handle { currentList, value: List<M> ->
        all() ?: initialData
    }

    override val get: Handler<List<M>> = handle { currentList, value: List<M> ->
        all() ?: initialData
    }

    protected open suspend fun all(): List<M>? =
        restService.all()

    override val set: Handler<List<M>> = handle { currentList: List<M>, value: List<M> ->
        value.also {
            it.forEach { it ->
                restService.save(it)
            }
        }
    }

    override val add: Handler<M> = handle { currentList: List<M>, value: M ->
        restService.create(value)
        if (currentList.contains(value)) currentList else currentList + value
    }

    override val remove: Handler<M> = handle { currentList: List<M>, value: M ->
        restService.delete(value.id!!)
        if (!currentList.contains(value)) currentList else currentList - value
    }

    override val clear: Handler<Unit> = handle { currentList: List<M>, value: Unit ->
        initialData
    }

    override val reset: Handler<Unit> = handle { currentList: List<M>, value: Unit ->
        listOf<M>()
    }

    override fun renderEach(
        renderContext: RenderContext,
        idProvider: IdProvider<M, *>?,
        into: Tag<HTMLElement>?,
        batch: Boolean,
        content: RenderContext.(M) -> Tag<HTMLElement>
    ): Unit =
        renderContext.run {
            data.renderEach(idProvider ?: { it.id }, into, batch, content)
        }

    fun renderIfEmpty(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(List<M>) -> Unit
    ): Unit =
        renderIf(renderContext, { it.isEmpty() }, into, content)

    init {
        data handledBy init
    }
}