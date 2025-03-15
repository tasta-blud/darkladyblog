package darkladyblog.darkladyblog.common.services

interface DescriptionShortener {
    fun shorten(description: String): String
}