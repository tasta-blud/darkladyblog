package darkladyblog.darkladyblog.common.log

import darkladyblog.darkladyblog.common.util.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

internal actual fun getLoggerImpl(name: String): Logger =
    LoggerImplJs(name)

private class LoggerImplJs(override val name: String) : Logger {

    private fun msg(level: String, o: Array<out Any?>): Array<Any?> =
        arrayOf(prefix(level)) + o

    @OptIn(FormatStringsInDatetimeFormats::class)
    private val format = LocalDateTime.Format {
        byUnicodePattern("uuuu-MM-dd' 'HH:mm[:ss[.SSS]]")
    }

    private fun prefix(level: String): String =
        buildList {
            add(format.format(LocalDateTime.now()))
            add("[$name]")
            add(level.take(5).padStart(5))
            add("--")
        }.joinToString(" ")

    override fun error(vararg o: Any?) =
        console.error(*msg("ERROR", o))

    override fun info(vararg o: Any?) =
        console.info(*msg("INFO", o))

    override fun log(vararg o: Any?) =
        console.log(*msg("LOG", o))

    override fun warn(vararg o: Any?) =
        console.warn(*msg("WARN", o))
}