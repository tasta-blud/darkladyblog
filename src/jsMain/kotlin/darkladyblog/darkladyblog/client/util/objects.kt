package darkladyblog.darkladyblog.client.util


@Suppress("NOTHING_TO_INLINE")
inline fun keys(json: dynamic): Array<String> = js("Object").keys(json).unsafeCast<Array<String>>()
