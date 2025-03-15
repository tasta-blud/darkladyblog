package darkladyblog.darkladyblog.client.util

import dev.fritz2.core.Listener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLElement

fun Listener<*, HTMLElement>.textContents(): Flow<String> =
    this.map { it.target.unsafeCast<HTMLElement>().textContent ?: "" }

fun Listener<*, HTMLElement>.htmls(): Flow<String> =
    this.map { it.target.unsafeCast<HTMLElement>().innerHTML }