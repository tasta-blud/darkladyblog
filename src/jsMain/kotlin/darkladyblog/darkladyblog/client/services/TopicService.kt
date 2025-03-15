package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.client.util.method
import darkladyblog.darkladyblog.common.model.app.TopicModel
import io.ktor.http.HttpMethod
import kotlinx.serialization.builtins.serializer

object TopicService : RestService<ULong, TopicModel>(ULong.serializer(), TopicModel.serializer(), "/topics") {

    suspend fun all(
        blogId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<String, String> = arrayOf("id" to "ASC"),
    ): List<TopicModel>? =
        all(offset, limit, *order) { method(HttpMethod.Get).append("/blog/$blogId") }

    suspend fun count(blogId: ULong): Long? =
        count { method(HttpMethod.Get).append("/blog/$blogId") }
}