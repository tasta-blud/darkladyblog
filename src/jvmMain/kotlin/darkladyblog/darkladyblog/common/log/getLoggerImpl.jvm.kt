package darkladyblog.darkladyblog.common.log

internal actual fun getLoggerImpl(name: String): Logger =
    LoggerImplJvm(name)

private class LoggerImplJvm(name: String) : Logger {
    private val delegate: org.slf4j.Logger =
        org.slf4j.LoggerFactory.getLogger(name)

    override val name: String =
        delegate.name

    private fun msg(vararg o: Any?): String =
        o.joinToString(" ")

    override fun error(vararg o: Any?) =
        delegate.error(msg(o))

    override fun info(vararg o: Any?) =
        delegate.info(msg(o))

    override fun log(vararg o: Any?) =
        delegate.info(msg(o))

    override fun warn(vararg o: Any?) =
        delegate.warn(msg(o))
}