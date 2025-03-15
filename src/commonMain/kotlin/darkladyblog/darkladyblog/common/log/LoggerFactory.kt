package darkladyblog.darkladyblog.common.log

interface LoggerFactory {

    fun getLogger(name: String): Logger

    companion object {
        private val defaultLoggerFactory: LoggerFactory by lazy {
            object : LoggerFactory {
                override fun getLogger(name: String): Logger {
                    return getLoggerImpl(name)
                }
            }
        }

        fun getLogger(name: String): Logger =
            defaultLoggerFactory.getLogger(name)
    }
}