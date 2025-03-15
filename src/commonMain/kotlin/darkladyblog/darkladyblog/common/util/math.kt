package darkladyblog.darkladyblog.common.util

import kotlin.math.max
import kotlin.math.min

fun Int.toRanged(range: ClosedRange<Int>): Int =
    max(range.start, min(this, range.endInclusive))

fun Long.toRanged(range: ClosedRange<Long>): Long =
    max(range.start, min(this, range.endInclusive))

fun Float.toRanged(range: ClosedRange<Float>): Float =
    max(range.start, min(this, range.endInclusive))

fun Double.toRanged(range: ClosedRange<Double>): Double =
    max(range.start, min(this, range.endInclusive))
