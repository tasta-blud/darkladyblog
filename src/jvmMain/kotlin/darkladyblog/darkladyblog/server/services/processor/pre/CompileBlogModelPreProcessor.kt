package darkladyblog.darkladyblog.server.services.processor.pre

import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.util.now
import darkladyblog.darkladyblog.common.util.toLocalizedString
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Single

@Single
class CompileBlogModelPreProcessor() : CompileModelPreProcessor<ULong, BlogModel>() {
    override fun accepts(model: Any): Boolean =
        model is BlogModel

    override fun process(model: BlogModel): BlogModel {
        var model = model
        model = model.copy(descriptionShortSource = shortener.shorten(model.descriptionLongSource))
        model = model.copy(descriptionLongCompiled = compiler.compile(model.descriptionLongSource))
        model = model.copy(descriptionShortCompiled = compiler.compile(model.descriptionShortSource))
        val now = LocalDateTime.now()
        model = model.copy(updatedAt = now, updatedAtString = now.toLocalizedString())
        return model
    }
}