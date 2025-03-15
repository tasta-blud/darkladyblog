package darkladyblog.darkladyblog.server.plugins.sessionscope

import io.ktor.server.application.ApplicationCall
import org.koin.core.Koin
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import org.koin.mp.KoinPlatformTools
import org.koin.mp.generateId

class SessionScope(private val _koin: Koin, call: ApplicationCall) : KoinScopeComponent {
    private val scopeId = "session_" + KoinPlatformTools.generateId()
    override fun getKoin(): Koin = _koin
    override val scope: Scope = createScope(scopeId = scopeId, source = call)
}