package darkladyblog.darkladyblog.common.log

interface Logger {
    val name: String
    fun error(vararg o: Any?): Unit
    fun info(vararg o: Any?): Unit
    fun log(vararg o: Any?): Unit
    fun warn(vararg o: Any?): Unit
}