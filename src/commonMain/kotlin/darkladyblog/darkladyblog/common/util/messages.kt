package darkladyblog.darkladyblog.common.util

import de.comahe.i18n4k.messages.MessageBundle


expect operator fun MessageBundle.get(key: String): String?
