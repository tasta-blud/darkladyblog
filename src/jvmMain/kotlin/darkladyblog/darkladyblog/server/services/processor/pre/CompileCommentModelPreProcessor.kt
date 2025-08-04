package darkladyblog.darkladyblog.server.services.processor.pre

import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.util.now
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Single

@Single
class CompileCommentModelPreProcessor() : CompileModelPreProcessor<ULong, CommentModel>() {
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