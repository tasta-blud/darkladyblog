package darkladyblog.darkladyblog.server.plugins.sessionscope

import io.ktor.server.application.ApplicationCall
import org.koin.core.scope.Scope

val ApplicationCall.sessionScope: Scope
    get() = this.attributes.getOrNull(KOIN_SESSION_SCOPE_ATTRIBUTE_KEY)
        ?: error("Koin Session Scope is not ready")