package darkladyblog.darkladyblog.server.base

import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.base.IdModel
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting
import darkladyblog.darkladyblog.common.ex.NoEntityException
import darkladyblog.darkladyblog.server.data.ModelPostProcessor
import darkladyblog.darkladyblog.server.data.ModelPreProcessor
import io.ktor.server.application.ApplicationCall
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.koin.core.annotation.KoinInternalApi
import org.jetbrains.exposed.sql.Column as EColumn
import org.jetbrains.exposed.sql.SortOrder as ESortOrder

abstract class RestController<TBL : IdTable<ID>, ID : Any, E : IdModel<ID>, R : Repository<TBL, ID, E>, S : RepositoryService<TBL, ID, E, R>>(
    val repositoryService: S,
    val call: ApplicationCall
) : IRestController<ID, E> {

    protected fun mapOrder(order: Array<out Sorting>): Array<Pair<EColumn<*>, ESortOrder>> =
        order.map { (column: ColumnName, order: SortDirection) -> mapColumn(column) to mapSortOrder(order) }
            .toTypedArray()

    protected fun mapColumn(column: ColumnName): EColumn<*> =
        repositoryService.repository.alias[repositoryService.repository.table.columns.first { it.name == column.name }]

    protected fun mapSortOrder(order: SortDirection): ESortOrder = when (order) {
        SortDirection.ASC -> ESortOrder.ASC
        SortDirection.DESC -> ESortOrder.DESC
    }

    override suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<E> =
        repositoryService.search(query, offset, limit, *mapOrder(order))

    override suspend fun all(offset: Long?, limit: Int?, order: Array<Sorting>): List<E> =
        repositoryService.all(offset, limit, *mapOrder(order), op = { Op.TRUE })

    fun get(op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }): E? =
        repositoryService.get(op)

    override suspend fun get(id: ID): Result<E> =
        repositoryService.get(id)?.let { Result.success(it) } ?: Result.failure(NoEntityException("$id"))

    override suspend fun create(model: E): ID =
        repositoryService.create(postProcess(call, preProcess(call, model)))

    override suspend fun update(model: E, id: ID): Int =
        repositoryService.update(postProcess(call, preProcess(call, model)), id)

    override suspend fun save(model: E, getID: E.() -> ID?): E =
        repositoryService.save(postProcess(call, preProcess(call, model)), getID)

    override suspend fun delete(id: ID): Int =
        repositoryService.delete(id)

    override suspend fun count(): Long =
        repositoryService.count()

    override suspend fun exists(id: ID): Boolean =
        repositoryService.exists(id)

    @OptIn(KoinInternalApi::class)
    private fun preProcess(call: ApplicationCall, model: E): E {
        var model = model
        getKoin().getAll<ModelPreProcessor<ID, E>>().forEach { updater ->
            if (updater.accepts(model)) model = updater.process(model)
        }
        return model
    }

    @OptIn(KoinInternalApi::class)
    private fun postProcess(call: ApplicationCall, model: E): E {
        var model = model
        getKoin().getAll<ModelPostProcessor<ID, E>>().forEach { updater ->
            if (updater.accepts(model)) model = updater.process(model)
        }
        return model
    }
}
