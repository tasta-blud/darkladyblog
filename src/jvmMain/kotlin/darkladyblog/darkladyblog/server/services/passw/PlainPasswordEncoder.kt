package darkladyblog.darkladyblog.server.services.passw

import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single(binds = [PasswordEncoder::class])
@Named("plain")
class PlainPasswordEncoder : PasswordEncoder {
    override val name: String
        get() = "plain"

    override fun encode(password: String): String {
        return "$name:$password"
    }

    override fun matches(password: String, encodedPassword: String): Boolean {
        return encode(password) == encodedPassword
    }
}
