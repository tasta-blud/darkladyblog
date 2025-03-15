package darkladyblog.darkladyblog.server.plugins.sessionscope

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.PluginBuilder
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.CallSetup
import io.ktor.server.application.hooks.ResponseSent
import io.ktor.server.application.plugin
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.sessionId
import io.ktor.util.AttributeKey
import org.koin.core.Koin
import org.koin.core.scope.Scope
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.koin

const val KOIN_SESSION_SCOPE_KEY: String = "KOIN_SESSION_SCOPE"
val KOIN_SESSION_SCOPE_ATTRIBUTE_KEY: AttributeKey<Scope> = AttributeKey<Scope>(KOIN_SESSION_SCOPE_KEY)

val KoinSessionScope: ApplicationPlugin<KoinSessionScopeConfiguration> =
    createApplicationPlugin("KoinSessionScope", createConfiguration = ::KoinSessionScopeConfiguration) {
        application.plugin(Koin)
        application.plugin(Sessions)
        val koin = application.koin()
        fun hasScope(call: ApplicationCall): Boolean {
            return call.attributes.contains(KOIN_SESSION_SCOPE_ATTRIBUTE_KEY)
        }

        fun closeScope(call: ApplicationCall) {
            call.attributes.getOrNull(KOIN_SESSION_SCOPE_ATTRIBUTE_KEY)?.close()
            call.attributes.remove(KOIN_SESSION_SCOPE_ATTRIBUTE_KEY)
        }

        fun createScope(koin: Koin, call: ApplicationCall) {
            val scopeComponent = SessionScope(koin, call)
            call.attributes.put(KOIN_SESSION_SCOPE_ATTRIBUTE_KEY, scopeComponent.scope)
        }

        fun PluginBuilder<KoinSessionScopeConfiguration>.getSessionIds(call: ApplicationCall): List<String?> =
            pluginConfig.sessionType.let { list ->
                if (list.isEmpty()) listOf(call.sessionId) else list.map { call.sessionId(it) }
            }

        on(CallSetup) { call ->
            getSessionIds(call).forEach { sessionId ->
                if (sessionId != null) {
                    if (hasScope(call)) closeScope(call)
                    createScope(koin, call)
                } else {
                    closeScope(call)
                }
            }
        }
        on(ResponseSent) { call ->
            getSessionIds(call).forEach { sessionId ->
                if (sessionId == null) closeScope(call)
            }
        }
    }