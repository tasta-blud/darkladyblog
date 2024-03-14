package com.github.tasta_blud.darkladyblog.server.services

object PlainPasswordEncoder : PasswordEncoder {
    override fun checkPassword(rawPassword: String, encodedPassword: String): Boolean =
        rawPassword == encodedPassword

    override fun encodePassword(rawPassword: String): String =
        rawPassword
}
