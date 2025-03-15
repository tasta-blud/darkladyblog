package darkladyblog.darkladyblog.client.util

import dev.fritz2.routing.decodeURIComponent
import dev.fritz2.routing.encodeURIComponent


fun decodeURI(uri: String): Map<String, String> =
    uri.split(uriDivider).filter { it.isNotBlank() }.asReversed().associate {
        if (uriAssignment in it) {
            it.substringBefore(uriAssignment) to decodeURIComponent(it.substringAfter(uriAssignment))
        } else {
            it to "true"
        }
    }

fun encodeURI(map: Map<String, String>): String =
    map.map { (key, value) -> key + uriAssignment + encodeURIComponent(value) }.joinToString(uriDivider)

const val uriQuery: Char = '?'
const val uriAssignment: Char = '='
const val uriDivider: String = "&"
