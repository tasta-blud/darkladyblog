package darkladyblog.darkladyblog.server.services.processor.pre

import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.common.util.now
import io.ktor.server.application.ApplicationCall
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import org.koin.ktor.plugin.RequestScope

@Scope(RequestScope::class)
@Scoped
class CompileTopicModelPreProcessor(call: ApplicationCall) : CompileModelPreProcessor<ULong, TopicModel>(call) {
    override fun accepts(model: Any): Boolean =
        model is TopicModel

    override fun process(model: TopicModel): TopicModel {
        var model = model
        model = model.copy(descriptionShortSource = shortener.shorten(model.descriptionLongSource))
        model = model.copy(descriptionLongCompiled = compiler.compile(model.descriptionLongSource))
        model = model.copy(descriptionShortCompiled = compiler.compile(model.descriptionShortSource))
        model = model.copy(updatedAt = LocalDateTime.now())
        return model
    }
}