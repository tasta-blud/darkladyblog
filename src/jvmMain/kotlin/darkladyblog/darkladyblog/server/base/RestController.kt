package darkladyblog.darkladyblog.server.base

import darkladyblog.darkladyblog.common.base.IdModel
import darkladyblog.darkladyblog.server.data.ModelPostProcessor
import darkladyblog.darkladyblog.server.data.ModelPreProcessor
import darkladyblog.darkladyblog.server.util.checkMy
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.koin.core.annotation.KoinInternalApi
import org.koin.ktor.plugin.scope

abstract class RestController<TBL : IdTable<ID>, ID : Any, E : IdModel<ID>, R : Repository<TBL, ID, E>, S : RepositoryService<TBL, ID, E, R>>(
    path: String,
    val repositoryService: S,
    val keySerializer: KSerializer<ID>,
    val valueSerializer: KSerializer<E>,
) : Controller(path) {

    override fun Route.routes() {
        restRoutes()
        additionalRoutes()
    }

    protected open fun Route.restRoutes() {
        get("/") {
            sendBodyList(
                call,
                all(
                    offset = offsetParam(call),
                    limit = limitParam(call),
                    order = orderParam(call) ?: arrayOf(),
                )
            )
        }
        get("/count") {
            call.respond(count())
        }
        get("/{id}") {
            get(idParam(call.parameters))?.let { sendBody(call, it) }
                ?: call.respond(HttpStatusCode.NotFound)
        }
        get("/{id}/exists") {
            call.respond(exists(idParam(call.parameters)))
        }
        post("/") {
            sendId(call, create(call, receiveBody(call)), HttpStatusCode.Created)
        }
        post("/{id}") {
            call.respond(update(call, receiveBody(call).also { it -> call.checkMy(it) }, idParam(call.parameters)))
        }
        post("/save") {
            receiveBody(call).also { it -> call.checkMy(it) }
                .let { model -> sendBody(call, save(call, model) { getId(model) }) }
        }
        delete("/{id}") {
            call.respond(delete(idParam(call.parameters)))
        }
    }

    protected open fun Route.additionalRoutes() {}

    protected open fun orderParam(call: RoutingCall, paramName: String = "order"): Array<Pair<Column<*>, SortOrder>>? =
        call.parameters[paramName]?.split(",")?.map {
            it.split(' ', limit = 2).let { list ->
                list[0].let { n -> repositoryService.repository.table.columns.first { c -> c.name == n } }.let { col ->
                    repositoryService.repository.alias[col]
                } to SortOrder.valueOf(list[1])
            }
        }?.toTypedArray()

    protected open fun offsetParam(call: RoutingCall, paramName: String = "offset"): Long? =
        call.parameters[paramName]?.toLongOrNull()

    protected open fun limitParam(call: RoutingCall, paramName: String = "limit"): Int? =
        call.parameters[paramName]?.toIntOrNull()

    protected open fun idParam(parameters: Parameters, paramName: String = "id"): ID =
        DefaultJson.decodeFromString(keySerializer, parameters[paramName]!!)

    protected open fun getId(model: E): ID? =
        model.id

    protected open suspend fun receiveBody(call: RoutingCall): E =
        DefaultJson.decodeFromString(valueSerializer, call.receiveText())

    protected open suspend fun sendId(
        call: RoutingCall,
        id: ID,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ) {
        call.respond(statusCode, DefaultJson.encodeToString(keySerializer, id))
    }

    protected open suspend fun sendBody(
        call: RoutingCall,
        model: E,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ) {
        call.respond(statusCode, DefaultJson.encodeToString(valueSerializer, model))
    }

    protected open suspend fun sendBodyList(
        call: RoutingCall,
        list: List<E>,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ) {
        call.respond(statusCode, DefaultJson.encodeToString(ListSerializer(valueSerializer), list))
    }

    fun all(
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(repositoryService.repository.alias[repositoryService.repository.table.id] to SortOrder.ASC),
        op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }
    ): List<E> =
        repositoryService.all(offset, limit, *order, op = op)

    fun get(
        op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE },
    ): E? =
        repositoryService.get(op)

    fun get(id: ID): E? =
        repositoryService.get(id)

    fun create(call: ApplicationCall, model: E): ID =
        repositoryService.create(postProcess(call, preProcess(call, model)))

    fun update(call: ApplicationCall, model: E, id: ID): Int =
        repositoryService.update(postProcess(call, preProcess(call, model)), id)

    fun save(call: ApplicationCall, model: E, getID: E.() -> ID?): E =
        repositoryService.save(postProcess(call, preProcess(call, model)), getID)

    fun delete(id: ID): Int =
        repositoryService.delete(id)

    fun count(): Long =
        repositoryService.count()

    fun exists(id: ID): Boolean =
        repositoryService.exists(id)

    @OptIn(KoinInternalApi::class)
    private fun preProcess(call: ApplicationCall, model: E): E {
        var model = model
        call.scope.getAll<ModelPreProcessor<ID, E>>().forEach { updater ->
            if (updater.accepts(model)) model = updater.process(model)
        }
        return model
    }

    @OptIn(KoinInternalApi::class)
    private fun postProcess(call: ApplicationCall, model: E): E {
        var model = model
        call.scope.getAll<ModelPostProcessor<ID, E>>().forEach { updater ->
            if (updater.accepts(model)) model = updater.process(model)
        }
        return model
    }
}
