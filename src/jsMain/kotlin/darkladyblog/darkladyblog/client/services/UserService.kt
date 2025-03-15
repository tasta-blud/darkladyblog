package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.common.model.UserModel
import kotlinx.serialization.builtins.serializer

object UserService : RestService<ULong, UserModel>(ULong.serializer(), UserModel.serializer(), "/users") {

}