package darkladyblog.darkladyblog.server.services.processor.post

import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.common.util.now
import io.ktor.server.application.ApplicationCall
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import org.koin.ktor.plugin.RequestScope

@Scope(RequestScope::class)
@Scoped
class ModifiedTopicModelPostProcessor(call: ApplicationCall) : ModifiedModelPostProcessor<ULong, TopicModel>(call) {
    override fun accepts(model: Any): Boolean =
        model is TopicModel

    override fun process(model: TopicModel): TopicModel {
        var model = model
        user()?.let {
            model = model.copy(updatedBy = it)
        }
        model = model.copy(updatedAt = LocalDateTime.now())
        return model
    }
}