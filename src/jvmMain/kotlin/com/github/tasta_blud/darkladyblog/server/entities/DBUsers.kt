package com.github.tasta_blud.darkladyblog.server.entities

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object Users : LongIdTable("USERS") {
    val username: Column<String> = varchar("username", 255)
    val password: Column<String> = varchar("password", 255)
}
