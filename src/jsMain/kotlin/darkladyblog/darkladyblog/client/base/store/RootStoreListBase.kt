package darkladyblog.darkladyblog.client.base.store

import dev.fritz2.core.Handler
import dev.fritz2.core.Id
import dev.fritz2.core.IdProvider
import dev.fritz2.core.RenderContext
import dev.fritz2.core.Tag
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLElement

open class RootStoreListBase<D>(
    initialData: List<D> = listOf<D>(),
    job: Job = Job(),
    override val id: String = Id.next()
) : RootStoreBase<List<D>>(initialData, job, id) {

    open val count: Flow<Long> =
        data.map { countIt() }

    protected open suspend fun countIt(): Long =
        current.size.toLong()

    open val init: Handler<List<D>> = handle { currentList: List<D>, value: List<D> ->
        currentList
    }

    open val get: Handler<List<D>> = handle { currentList: List<D>, value: List<D> ->
        currentList
    }

    open val set: Handler<List<D>> = handle { currentList: List<D>, value: List<D> ->
        value
    }

    open val add: Handler<D> = handle { currentList: List<D>, value: D ->
        if (currentList.contains(value)) currentList else currentList + value
    }

    open val remove: Handler<D> = handle { currentList: List<D>, value: D ->
        if (!currentList.contains(value)) currentList else currentList - value
    }

    open val clear: Handler<Unit> = handle { currentList: List<D>, value: Unit ->
        listOf()
    }

    open val reset: Handler<Unit> = handle { currentList: List<D>, value: Unit ->
        initialData
    }

    open fun renderEach(
        renderContext: RenderContext,
        idProvider: IdProvider<D, *>? = null,
        into: Tag<HTMLElement>? = null,
        batch: Boolean = false,
        content: RenderContext.(D) -> Tag<HTMLElement>
    ): Unit =
        renderContext.run {
            data.renderEach(idProvider, into, batch, content)
        }
}