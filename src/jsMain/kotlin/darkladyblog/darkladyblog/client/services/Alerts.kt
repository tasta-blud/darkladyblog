package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.util.raw
import darkladyblog.darkladyblog.common.config.DEBUG
import darkladyblog.darkladyblog.common.log.log
import dev.fritz2.core.joinClasses
import dev.fritz2.core.type
import dev.fritz2.headless.components.toast

object Alerts {

    fun alert(
        message: String,
        header: String? = null,
        icon: String? = null,
        closeable: Boolean = true,
        raw: Boolean = false,
        duration: Long = 10000L,
        classes: String? = null
    ): Unit =
        toast("default", duration) {
            className(joinClasses("toast d-block show align-items-center border-0", classes ?: ""))
            attr("role", "alert")
            attr("aria-live", "assertive")
            attr("aria-atomic", "true")
            div("toast-header") {
                className(joinClasses(classes ?: ""))
                if (icon != null) {
                    i("bi bi-$icon") {}
                }
                if (header != null) {
                    +header
                }
                if (closeable) {
                    button("btn-close btn-close-white me-2 m-auto") {
                        type("button")
                        attr("data-bs-dismiss", "toast")
                        attr("aria-label", "Close")
                        clicks handledBy close
                    }
                }
            }
            div("toast-body overflow-hidden") {
                if (raw)
                    raw(message)
                else message.split('\n').forEach { line ->
                    +line
                    br {}
                }
            }
        }

    fun success(
        message: String,
        header: String? = null,
        icon: String? = null,
        closeable: Boolean = true,
        raw: Boolean = false,
        duration: Long = 10000L
    ): Unit =
        alert(message, header, icon ?: "info-circle-fill", closeable, raw, duration, "text-bg-success")

    fun info(
        message: String,
        header: String? = null,
        icon: String? = null,
        closeable: Boolean = true,
        raw: Boolean = false,
        duration: Long = 10000L
    ): Unit =
        alert(message, header, icon ?: "info-triangle-fill", closeable, raw, duration, "text-bg-info")

    fun warning(
        message: String,
        header: String? = null,
        icon: String? = null,
        closeable: Boolean = true,
        raw: Boolean = false,
        duration: Long = 10000L
    ): Unit =
        alert(message, header, icon ?: "info-triangle-fill", closeable, raw, duration, "text-bg-warning")

    fun error(
        message: String,
        header: String? = null,
        icon: String? = null,
        closeable: Boolean = true,
        raw: Boolean = false,
        duration: Long = 10000L,
    ): Unit =
        alert(message, header, icon ?: "info-triangle-fill", closeable, raw, duration, "text-bg-danger").also {
            log.error(message, header)
        }

    fun error(ex: Throwable): Unit =
        error(if (DEBUG) ex.stackTraceToString() else "", ex.message)
}
