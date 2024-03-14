package com.github.tasta_blud.darkladyblog.server.repositories

import com.github.tasta_blud.darkladyblog.server.data.User
import com.github.tasta_blud.darkladyblog.server.entities.Users
import com.github.tasta_blud.darkladyblog.server.services.Repository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.koin.core.annotation.Factory

@Factory
class UserRepository : Repository<Long, Users, User>(Users) {

    override fun Users.update(it: UpdateBuilder<*>, entity: User) {
        it[username] = entity.username
        it[password] = entity.password
    }

    override fun Users.transform(row: ResultRow): User =
        User(
            row[username],
            row[password],
        )

    fun findByUsername(username: String): User? =
        findOne { Users.username eq username }
}
