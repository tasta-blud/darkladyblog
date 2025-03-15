package darkladyblog.darkladyblog.client.base.store

import dev.fritz2.core.Handler
import dev.fritz2.core.Id
import kotlinx.coroutines.Job
import org.w3c.dom.events.MouseEvent

class BooleanRootStore(initialData: Boolean, job: Job = Job(), override val id: String = Id.next()) :
    RootStoreBase<Boolean>(initialData, job, id) {
    val switch: Handler<MouseEvent> = handle { it, e -> !it }
}