package darkladyblog.darkladyblog.common.util

inline fun <T> Result<T>.filter(predicate: (T) -> Boolean): Result<T> =
    if (map(predicate).getOrDefault(false)) this else Result.failure(Exception())

inline fun <T> Result<T>.also(block: (T) -> Unit): Result<T> =
    map { block(it).run { it } }

inline fun <T> Result<T>.alsoCatching(block: (T) -> Unit): Result<T> =
    mapCatching { block(it).run { it } }

fun <T> Result<T?>.filterNotNull(): Result<T> =
    if (map { it != null }.getOrDefault(false)) map { it!! } else Result.failure(Exception())

fun <T> Result<T?>.filterNotNull(throwable: Throwable): Result<T> =
    if (map { it != null }.getOrDefault(false)) map { it!! } else Result.failure(throwable)

inline fun <T> Result<T?>.filterNotNull(throwable: () -> Throwable): Result<T> =
    if (map { it != null }.getOrDefault(false)) map { it!! } else Result.failure(throwable())