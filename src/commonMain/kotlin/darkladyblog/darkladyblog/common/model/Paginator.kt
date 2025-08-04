package darkladyblog.darkladyblog.common.model

import darkladyblog.darkladyblog.common.util.toRanged
import dev.fritz2.core.Lenses
import kotlinx.serialization.Serializable
import kotlin.math.ceil
import kotlin.math.floor

@Lenses
@Serializable
data class Paginator(val count: Long = 0, val offset: Long = 0, val limit: Int? = null) {

    constructor(count: Long = 0, page: Int = 1, limit: Int? = null) :
            this(
                count,
                if (limit == null) 0 else (page.toRanged(1..ceil(count / limit.toDouble()).toInt()) - 1) * limit.toLong(),
                limit
            )

    constructor(count: Long = 0, limit: Int? = null) :
            this(count, 0, limit)

    constructor(count: Long = 0) :
            this(count, 1, null)

    val pages: Int
        get() = if (limit == null) 1 else ceil(count / limit.toDouble()).toInt()

    val page: Int
        get() = if (limit == null) 1 else floor(offset / limit.toDouble()).toInt() + 1

    val isFirstPage: Boolean
        get() = page == 1

    val isLastPage: Boolean
        get() = page == pages

    fun copy(page: Int): Paginator =
        copy(offset = if (limit == null) 0 else (page.toRanged(1..pages) - 1) * limit.toLong())

    override fun toString(): String =
        "Paginator(count=$count, offset=$offset, limit=$limit, pages=$pages, page=$page, isFirstPage=$isFirstPage, isLastPage=$isLastPage)"

    companion object
}