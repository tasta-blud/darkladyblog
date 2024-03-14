package com.github.tasta_blud.darkladyblog.server.services

object DelegatingPasswordEncoder : PasswordEncoder {
    private val encoders: Map<String, PasswordEncoder> = mapOf(
        "" to PlainPasswordEncoder
    )
    var defaultEncoder: PasswordEncoder = PlainPasswordEncoder
        set(value) {
            require(value in encoders.values)
            field = value
        }

    override fun checkPassword(rawPassword: String, encodedPassword: String): Boolean =
        defaultEncoder.checkPassword(
            rawPassword,
            removePrefix(encodedPassword, getPrefixByEncoder(defaultEncoder))
        )

    override fun encodePassword(rawPassword: String): String =
        getPrefixByEncoder(defaultEncoder) + ':' + defaultEncoder.encodePassword(rawPassword)

    private fun getPrefixByEncoder(encoder: PasswordEncoder): String =
        encoders.filter { it.value == encoder }.firstNotNullOf { it.key }

    private fun removePrefix(password: String, prefix: String): String =
        if (password.startsWith("$prefix:")) password.removePrefix("$prefix:") else password
}
