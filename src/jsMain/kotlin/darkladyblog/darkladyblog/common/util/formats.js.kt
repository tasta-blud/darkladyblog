package darkladyblog.darkladyblog.common.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat

actual fun LocalDateTime.toLocalizedString(format: DateTimeFormat<LocalDateTime>, locale: String): String =
    runCatching { format.format(this) }.getOrElse { this.toString() }