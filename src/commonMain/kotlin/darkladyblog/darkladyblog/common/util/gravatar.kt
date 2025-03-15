package darkladyblog.darkladyblog.common.util

const val GRAVATAR_AVATAR: String = "https://gravatar.com/avatar/"

fun getGravatarURL(email: String, size: Int? = null, default: String? = null): String =
    GRAVATAR_AVATAR + sha256(email.lowercase()) + '?'
        .let { if (size != null) "$it&s=$size" else it }
        .let { if (default != null) "$it&d=$default" else it }
