package darkladyblog.darkladyblog.server.services.processor.post

import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.util.now
import darkladyblog.darkladyblog.server.util.loggedUser
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import org.koin.ktor.plugin.RequestScope

@Scope(RequestScope::class)
@Scoped
class ModifiedCommentModelPostProcessor() : ModifiedModelPostProcessor<ULong, CommentModel>() {
    override fun accepts(model: Any): Boolean =
        model is CommentModel

    override fun process(model: CommentModel): CommentModel {
        var model = model
        loggedUser?.let {
            model = model.copy(updatedBy = it)
        }
        model = model.copy(updatedAt = LocalDateTime.now())
        return model
    }
}