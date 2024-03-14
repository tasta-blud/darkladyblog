package com.github.tasta_blud.darkladyblog.server.services

import com.github.tasta_blud.darkladyblog.server.data.User
import com.github.tasta_blud.darkladyblog.server.repositories.UserRepository
import org.koin.core.annotation.Factory

@Factory
class UserService(private val repository: UserRepository) {

    var passwordEncoder: PasswordEncoder = DelegatingPasswordEncoder

    fun createUser(user: User) {
        repository.create(User(user.username, passwordEncoder.encodePassword(user.password)))
    }

    fun findUserByUsernameAndPassword(username: String, password: String): User? {
        val user = repository.findByUsername(username) ?: return null
        return if (passwordEncoder.checkPassword(password, user.password)) User(user.username, "") else null
    }
}
