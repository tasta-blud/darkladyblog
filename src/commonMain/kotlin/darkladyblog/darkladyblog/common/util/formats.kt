package darkladyblog.darkladyblog.common.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat

expect fun LocalDateTime.toLocalizedString(
    format: DateTimeFormat<LocalDateTime> = LocalDateTime.Formats.ISO,
    locale: String = ""
): String