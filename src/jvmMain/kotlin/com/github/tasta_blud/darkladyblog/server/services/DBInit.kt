package com.github.tasta_blud.darkladyblog.server.services

import com.github.tasta_blud.darkladyblog.server.data.User
import com.github.tasta_blud.darkladyblog.server.repositories.UserRepository
import org.koin.core.annotation.Factory
import org.koin.java.KoinJavaComponent

@Factory
class DBInit(private val userRepository: UserRepository, private val userService: UserService) {
    fun init(): Unit {
        if (userRepository.findAll().isEmpty()) {
            val userService: UserService by KoinJavaComponent.inject(UserService::class.java)
            userService.createUser(User("user", "user"))
        }
    }
}
