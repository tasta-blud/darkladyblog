package darkladyblog.darkladyblog.server.services.processor.post

import darkladyblog.darkladyblog.common.base.Modified
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.data.ModelPostProcessor
import darkladyblog.darkladyblog.server.util.getUser
import io.ktor.server.application.ApplicationCall
import kotlinx.coroutines.runBlocking

abstract class ModifiedModelPostProcessor<ID : Any, M : Modified<ID>>(val call: ApplicationCall) :
    ModelPostProcessor<ID, M> {
    fun user(): UserModel? = runBlocking { call.getUser() }
}