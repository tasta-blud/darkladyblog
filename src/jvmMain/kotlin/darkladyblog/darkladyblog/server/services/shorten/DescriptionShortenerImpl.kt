package darkladyblog.darkladyblog.server.services.shorten

import darkladyblog.darkladyblog.common.services.DescriptionShortener
import org.koin.core.annotation.Single

@Single
class DescriptionShortenerImpl : DescriptionShortener {
    override fun shorten(description: String): String =
        description.substringBefore("---") // FIXME
}