package darkladyblog.darkladyblog.client.base.store

import darkladyblog.darkladyblog.client.data.PageRouter
import darkladyblog.darkladyblog.client.store.TitleStore
import darkladyblog.darkladyblog.client.util.runLaterTimed
import dev.fritz2.core.Id
import dev.fritz2.core.RenderContext
import dev.fritz2.core.RootStore
import dev.fritz2.core.Tag
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLElement

open class RootStoreBase<D>(val initialData: D, job: Job = Job(), override val id: String = Id.next()) :
    RootStore<D>(initialData, job, id) {
    init {
        data.combine(PageRouter.data) { model, pageData ->
            model to titleArgs(model)?.let { args -> PageRouter.findPage(pageData).title(args) }
        }.filter { (model, title) ->
            title != null && title != current
        }.map { (model, title) ->
            title!!
        } handledBy TitleStore.update
    }

    protected open fun titleArgs(current: D): List<String>? =
        null

    infix fun initialize(execute: suspend (D) -> Unit) {
        data handledBy execute
    }

    fun RenderContext.useEffect(op: suspend () -> Unit) {
        data.render {
            runLaterTimed(op = op)
        }
    }

    fun render(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(D) -> Unit
    ) {
        renderContext.run {
            data.render(into) {
                content(it)
            }
        }
    }

    fun renderIf(
        renderContext: RenderContext,
        predicate: (D) -> Boolean,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(D) -> Unit
    ) {
        render(renderContext, into) {
            val result = predicate(it)
            if (result) {
                content(it)
            }
        }
    }

    fun renderNotIf(
        renderContext: RenderContext,
        predicate: (D) -> Boolean,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(D) -> Unit
    ): Unit =
        render(renderContext, into) {
            val result = predicate(it)
            if (!result) {
                content(it)
            }
        }

    fun renderNull(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.() -> Unit
    ): Unit =
        render(renderContext, into) {
            val result = it == null
            if (result) {
                content()
            }
        }

    fun renderNotNull(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(D) -> Unit
    ): Unit =
        render(renderContext, into) {
            val result = it != null
            if (result) {
                content(it)
            }
        }

    fun renderTrue(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(D) -> Unit
    ): Unit =
        render(renderContext, into) {
            val result = (it is Boolean && it) || (it !is Boolean && it != null)
            if (result) {
                content(it)
            }
        }

    fun renderFalse(
        renderContext: RenderContext,
        into: Tag<HTMLElement>? = null,
        content: Tag<*>.(D) -> Unit
    ): Unit =
        render(renderContext, into) {
            val result = (it is Boolean && it) || (it !is Boolean && it != null)
            if (!result) {
                content(it)
            }
        }
}