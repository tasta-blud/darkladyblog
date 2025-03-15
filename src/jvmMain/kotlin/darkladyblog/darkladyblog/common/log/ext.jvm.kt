package darkladyblog.darkladyblog.common.log

actual val <C : Any> C.log: Logger
    get() = LoggerFactory.getLogger(this::class.qualifiedName!!)