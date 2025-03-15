package darkladyblog.darkladyblog.client.util

import dev.fritz2.core.RenderContext
import dev.fritz2.core.Scope
import dev.fritz2.core.ScopeContext
import dev.fritz2.core.SvgTag
import kotlinx.coroutines.flow.Flow

private inline fun RenderContext.evalScope(context: (ScopeContext.() -> Unit)): Scope =
    ScopeContext(scope).apply(context).scope


fun SvgTag.symbol(
    baseClass: String? = null,
    id: String? = null,
    scope: (ScopeContext.() -> Unit) = {},
    content: SvgTag.() -> Unit
): SvgTag =
    register(SvgTag("symbol", id, baseClass, job = job, evalScope(scope)), content)

fun SvgTag.use(
    baseClass: String? = null,
    id: String? = null,
    scope: (ScopeContext.() -> Unit) = {},
    content: SvgTag.() -> Unit
): SvgTag =
    register(SvgTag("use", id, baseClass, job = job, evalScope(scope)), content)

fun SvgTag.href(value: String): Unit = attr("href", value)
fun SvgTag.href(value: Flow<String>): Unit = attr("href", value)

fun SvgTag.width(value: String): Unit = attr("width", value)
fun SvgTag.width(value: Flow<String>): Unit = attr("width", value)

fun SvgTag.height(value: String): Unit = attr("height", value)
fun SvgTag.height(value: Flow<String>): Unit = attr("height", value)
