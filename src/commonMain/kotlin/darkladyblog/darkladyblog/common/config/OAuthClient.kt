package darkladyblog.darkladyblog.common.config

import darkladyblog.darkladyblog.common.util.toWordsByChars

enum class OAuthClient(icon: String = "", label: String = "", url: String = "") {
    GOOGLE;

    val label: String = label.ifEmpty { name.toWordsByChars() }
    val icon: String = icon.ifEmpty { "bi bi-" + this.name.lowercase() }
    val url: String = url.ifEmpty { "/${name.lowercase()}/signin" }
}