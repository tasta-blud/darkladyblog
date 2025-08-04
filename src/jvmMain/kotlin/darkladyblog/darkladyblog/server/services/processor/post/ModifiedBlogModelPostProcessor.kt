package darkladyblog.darkladyblog.server.services.processor.post

import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.util.now
import darkladyblog.darkladyblog.common.util.toLocalizedString
import darkladyblog.darkladyblog.server.util.loggedUser
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import org.koin.ktor.plugin.RequestScope

@Scope(RequestScope::class)
@Scoped
class ModifiedBlogModelPostProcessor() : ModifiedModelPostProcessor<ULong, BlogModel>() {
    override fun accepts(model: Any): Boolean =
        model is BlogModel

    override fun process(model: BlogModel): BlogModel {
        var model = model
        loggedUser?.let {
            model = model.copy(updatedBy = it)
        }
        val now = LocalDateTime.now()
        model = model.copy(updatedAt = now, updatedAtString = now.toLocalizedString())
        return model
    }
}