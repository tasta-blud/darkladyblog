package darkladyblog.darkladyblog.common.util

import kotlinx.browser.document
import org.w3c.dom.get

actual val dataType: String
    get() = document.body!!.dataset["type"]!!