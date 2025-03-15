package darkladyblog.darkladyblog.client.util

inline fun <T> jsApply(self: dynamic, init: T.() -> Unit): T {
    init(self.unsafeCast<T>())
    return self.unsafeCast<T>()
}

inline fun <T> jsLet(self: dynamic, init: (T) -> Unit): T {
    init(self.unsafeCast<T>())
    return self.unsafeCast<T>()
}

fun <T> T.jsCreate(init: (T.() -> Unit) = {}): T = unsafeCast<T>().also { init(it) }
