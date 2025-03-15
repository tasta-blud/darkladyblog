package darkladyblog.darkladyblog.client.base.rest

import darkladyblog.darkladyblog.client.util.method
import darkladyblog.darkladyblog.common.base.IdModel
import dev.fritz2.remote.Request
import dev.fritz2.remote.bodyJson
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

abstract class RestService<ID : Any, E : IdModel<ID>>(
    val keySerializer: KSerializer<ID>,
    val valueSerializer: KSerializer<E>,
    path: String,
    useCookies: Boolean = true,
    useAuth: Boolean = true
) :
    AbstractHttpService(path, useCookies, useAuth) {

    suspend fun count(): Long? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request({ method(HttpMethod.Get).append("/count") }) {
                body().toLong()
            }.getOrNull()

    suspend fun exists(id: ID): Boolean? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request({ method(HttpMethod.Get).append("/$id/exists") }) {
                body().toBoolean()
            }.getOrNull()

    suspend fun get(id: ID): E? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request(valueSerializer) { method(HttpMethod.Get).append("/$id") }.getOrNull()

    suspend fun all(
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<String, String> = arrayOf("id" to "ASC"),
        filter: suspend Request.() -> Request = { method(HttpMethod.Get).append("/") }
    ): List<E>? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request(ListSerializer(valueSerializer)) {
                method(HttpMethod.Get).filter().queryParameters(buildMap {
                    if (offset != null) put("offset", offset.toString())
                    if (limit != null) put("limit", limit.toString())
                    if (order.isNotEmpty())
                        put("order", order.joinToString(",") { it.first + " " + it.second })
                })
            }.map { it ?: listOf() }.getOrElse { listOf() }

    suspend fun count(
        filter: suspend Request.() -> Request = { method(HttpMethod.Get).append("/count") }
    ): Long? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request({ method(HttpMethod.Get).filter().append("/count") }) {
                body().toLong()
            }.getOrNull()

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun create(model: E): ID? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .bodyJson(model, valueSerializer)
            .requestOf(keySerializer) {
                method(HttpMethod.Post).append("")
            }.getOrNull()

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun update(model: E, id: ID): E? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .bodyJson(model, valueSerializer)
            .request(valueSerializer) { method(HttpMethod.Post).append("/$id") }.getOrNull()

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun save(model: E): E? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .bodyJson(model, valueSerializer)
            .requestOf(valueSerializer) { method(HttpMethod.Post).append("/save") }.getOrNull()

    suspend fun delete(id: ID): Int? =
        http.useToastHttp()
            .acceptJson().contentType(ContentType.Application.Json.toString())
            .request({ method(HttpMethod.Delete).append("/$id") }) {
                body().toInt()
            }.getOrNull()
}