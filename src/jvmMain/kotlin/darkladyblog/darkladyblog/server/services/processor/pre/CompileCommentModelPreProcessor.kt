package darkladyblog.darkladyblog.server.services.processor.pre

import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.util.now
import io.ktor.server.application.ApplicationCall
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import org.koin.ktor.plugin.RequestScope

@Scope(RequestScope::class)
@Scoped
class CompileCommentModelPreProcessor(call: ApplicationCall) : CompileModelPreProcessor<ULong, CommentModel>(call) {
    override fun accepts(model: Any): Boolean =
        model is CommentModel

    override fun process(model: CommentModel): CommentModel {
        var model = model
        model = model.copy(descriptionShortSource = shortener.shorten(model.descriptionLongSource))
        model = model.copy(descriptionLongCompiled = compiler.compile(model.descriptionLongSource))
        model = model.copy(descriptionShortCompiled = compiler.compile(model.descriptionShortSource))
        model = model.copy(updatedAt = LocalDateTime.now())
        return model
    }
}