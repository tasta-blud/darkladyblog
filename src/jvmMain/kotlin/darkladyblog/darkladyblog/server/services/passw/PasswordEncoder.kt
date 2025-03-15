package darkladyblog.darkladyblog.server.services.passw

interface PasswordEncoder {
    val name: String
    fun encode(password: String): String
    fun matches(password: String, encodedPassword: String): Boolean
}
