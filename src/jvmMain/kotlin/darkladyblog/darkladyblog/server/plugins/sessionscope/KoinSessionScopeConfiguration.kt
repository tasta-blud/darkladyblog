package darkladyblog.darkladyblog.server.plugins.sessionscope

import kotlin.reflect.KClass

class KoinSessionScopeConfiguration {
    var sessionType: MutableList<KClass<out Any>> = mutableListOf()
}