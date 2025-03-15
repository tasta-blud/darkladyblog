package darkladyblog.darkladyblog.client.store

import darkladyblog.darkladyblog.client.base.store.RootStoreBase
import darkladyblog.darkladyblog.client.services.UserService
import darkladyblog.darkladyblog.common.model.Registration
import dev.fritz2.core.Handler
import dev.fritz2.core.Id
import kotlinx.coroutines.Job

open class RegistrationStore(initialData: Registration, job: Job = Job(), id: String = Id.next()) :
    RootStoreBase<Registration>(initialData, job, id) {

    val set: Handler<Registration> = handle { current, value: Registration ->
        UserService.save(value.toUserModel())?.let { Registration.NULL_REGISTRATION }
            ?: current
    }

}