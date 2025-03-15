package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.client.util.method
import darkladyblog.darkladyblog.common.model.app.CommentModel
import io.ktor.http.HttpMethod
import kotlinx.serialization.builtins.serializer

object CommentService : RestService<ULong, CommentModel>(ULong.serializer(), CommentModel.serializer(), "/comments") {

    suspend fun all(
        topicId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<String, String> = arrayOf("id" to "ASC"),
    ): List<CommentModel>? =
        all(offset, limit, *order) { method(HttpMethod.Get).append("/topic/$topicId") }

    suspend fun all(
        topicId: ULong,
        commentId: ULong? = null,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<String, String> = arrayOf("id" to "ASC"),
    ): List<CommentModel>? =
        all(offset, limit, *order) { method(HttpMethod.Get).append("/topic/$topicId/$commentId") }

    suspend fun count(topicId: ULong): Long? =
        count { method(HttpMethod.Get).append("/topic/$topicId") }

    suspend fun count(topicId: ULong, commentId: ULong? = null): Long? =
        count { method(HttpMethod.Get).append("/topic/$topicId/$commentId") }

}