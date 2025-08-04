package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.common.controllers.IUserRestController
import darkladyblog.darkladyblog.common.model.UserModel
import dev.kilua.rpc.getService

object UserService : RestService<ULong, UserModel, IUserRestController>(controller = getService<IUserRestController>())