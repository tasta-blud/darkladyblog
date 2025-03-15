package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.base.RestController
import darkladyblog.darkladyblog.server.db.Users
import darkladyblog.darkladyblog.server.repositories.UserRepository
import darkladyblog.darkladyblog.server.services.UserRepositoryService
import kotlinx.serialization.builtins.serializer
import org.koin.core.annotation.Single

@Single
class UserRestController(service: UserRepositoryService) :
    RestController<Users, ULong, UserModel, UserRepository, UserRepositoryService>(
        "/users",
        service,
        ULong.serializer(),
        UserModel.serializer()
    )
