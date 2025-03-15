package darkladyblog.darkladyblog.common.util

import org.kotlincrypto.hash.sha2.SHA256

fun sha256(text: String): String =
    SHA256().run {
        update(text.encodeToByteArray())
        digest()
    }.encodeToHex()

@OptIn(ExperimentalUnsignedTypes::class)
fun ByteArray.encodeToHex(): String {
    return asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }
}
