package darkladyblog.darkladyblog.common.util

import de.comahe.i18n4k.messages.MessageBundle
import de.comahe.i18n4k.messages.MessageBundleLocalizedString
import de.comahe.i18n4k.strings.LocalizedString
import kotlin.reflect.KProperty1


@Suppress("UNCHECKED_CAST")
actual operator fun MessageBundle.get(key: String): String? = getEntryByKey(key)?.let {
    this::class.members
        .filter { it is KProperty1<*, *> }
        .first { it.name == key.replace('-', '_') }
        .let { it as KProperty1<MessageBundle, MessageBundleLocalizedString> }.get(this)
        .let { it as LocalizedString }
        .invoke()
}
