package darkladyblog.darkladyblog.common.util

import darkladyblog.darkladyblog.client.util.keys
import de.comahe.i18n4k.messages.MessageBundle


actual operator fun MessageBundle.get(key: String): String? = getEntryByKey(key)?.let {
    val first = keys(this).first { it.startsWith("${key}_") }
    val string = " " + asDynamic()[first]
    string
}
