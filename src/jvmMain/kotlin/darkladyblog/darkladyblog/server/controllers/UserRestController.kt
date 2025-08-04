package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.controllers.IUserRestController
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.base.RestController
import darkladyblog.darkladyblog.server.db.Users
import darkladyblog.darkladyblog.server.repositories.UserRepository
import darkladyblog.darkladyblog.server.services.UserRepositoryService
import io.ktor.server.application.ApplicationCall
import org.koin.core.annotation.Single

@Single
class UserRestController(service: UserRepositoryService, call: ApplicationCall) :
    RestController<Users, ULong, UserModel, UserRepository, UserRepositoryService>(service, call), IUserRestController
