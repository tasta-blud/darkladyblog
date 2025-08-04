package darkladyblog.darkladyblog.common.data

import darkladyblog.darkladyblog.common.model.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val user: UserModel?, val accessToken: String)