package darkladyblog.darkladyblog.common.util

fun String.toWordsByChars(): String =
    lowercase().replaceFirstChar { it.uppercase() }.replace(Regex("[-_].")) { it.value.uppercase() }

fun String.toWordsByCase(): String =
    lowercase().replaceFirstChar { it.uppercase() }
        .map { if (it.isUpperCase()) " " + it.uppercase() else "" + it.lowercase() }.joinToString("")