package darkladyblog.darkladyblog.server.util

import kotlin.reflect.KClass
import kotlin.reflect.KType


inline val KClass<*>.isInterface: Boolean
    get() =
        java.isInterface

inline val KType.isInterface: Boolean
    get() =
        (classifier as KClass<*>).java.isInterface

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> KType.toKClass(): KClass<T> = classifier as KClass<T>