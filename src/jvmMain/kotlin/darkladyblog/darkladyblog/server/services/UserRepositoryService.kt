package darkladyblog.darkladyblog.server.services

import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.server.base.RepositoryService
import darkladyblog.darkladyblog.server.db.Users
import darkladyblog.darkladyblog.server.repositories.UserRepository
import darkladyblog.darkladyblog.server.services.passw.PasswordEncoder
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.qualifier.qualifier

@Single
class UserRepositoryService(userRepository: UserRepository) :
    RepositoryService<Users, ULong, UserModel, UserRepository>(userRepository) {
    var defaultPasswordEncoder: PasswordEncoder = get<PasswordEncoder>(qualifier("plain"))
    private val passwordEncoders: List<PasswordEncoder> = getKoin().getAll<PasswordEncoder>()

    fun getByUsername(username: String): UserModel? =
        get { repository.alias[repository.table.username] eq username }

    fun getByUsername(username: String, password: String): UserModel? {
        val user: UserModel = getByUsername(username) ?: return null
        val userPassword = user.password
        val prefix = userPassword.substringBefore(':')
        val passwordEncoder =
            passwordEncoders.firstOrNull { it.name == prefix } ?: defaultPasswordEncoder
        val matches = passwordEncoder.matches(password, userPassword)
        return user.takeIf { matches }
    }

    fun updatePassword(username: String, password: String) {
        val user: UserModel = getByUsername(username) ?: return
        update(user.copy(password = encodePassword(user, password)), user.id!!)
    }

    private fun encodePassword(user: UserModel, password: String): String {
        val prefix = user.password.substringBefore(':')
        val passwordEncoder =
            passwordEncoders.firstOrNull { it.name == prefix } ?: defaultPasswordEncoder
        return passwordEncoder.encode(password)
    }


    fun checkPassword(user: UserModel, password: String): Boolean {
        return passwordEncoders.firstOrNull {
            user.password.startsWith("${it.name}:")
        }?.matches(password, user.password) == true
    }
}
