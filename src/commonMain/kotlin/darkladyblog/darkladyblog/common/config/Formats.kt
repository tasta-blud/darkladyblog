package darkladyblog.darkladyblog.common.config

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

@OptIn(FormatStringsInDatetimeFormats::class)
object Formats {

    val systemFormat: DateTimeFormat<LocalDateTime> = LocalDateTime.Formats.ISO

    val userFormat: DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(", ")
        day()
        char(' ')
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        year()
        char(' ')
        hour()
        char(':')
        minute()
    }
}