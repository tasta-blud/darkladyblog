package darkladyblog.darkladyblog.server.data

import darkladyblog.darkladyblog.common.base.IdModel
import org.koin.core.component.KoinComponent

interface ModelProcessor<ID : Any, E : IdModel<ID>> : KoinComponent {
    fun accepts(model: Any): Boolean
    fun process(model: E): E
}