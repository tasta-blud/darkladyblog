package com.github.tasta_blud.darkladyblog.server.services

interface PasswordEncoder {
    fun checkPassword(rawPassword: String, encodedPassword: String): Boolean
    fun encodePassword(rawPassword: String): String
}
