package darkladyblog.darkladyblog.server.base

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.koin.core.component.KoinComponent

abstract class RepositoryService<TBL : IdTable<ID>, ID : Any, E : Any, R : Repository<TBL, ID, E>>(val repository: R) :
    KoinComponent {

    fun search(
        query: String,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(repository.alias[repository.table.id] to SortOrder.ASC),
    ): List<E> =
        repository.search(query, offset, limit, *order)

    fun all(
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(repository.alias[repository.table.id] to SortOrder.ASC),
        op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }
    ): List<E> =
        repository.all(offset, limit, *order, op = op)

    fun get(op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }): E? =
        repository.get(op)

    fun get(id: ID): E? =
        repository.get(id)

    fun create(model: E): ID =
        repository.create(model)

    fun createReturning(model: E, copy: E.(ID) -> E): E =
        repository.createReturning(model, copy)

    fun update(model: E, op: SqlExpressionBuilder.() -> Op<Boolean>): Int =
        repository.update(model, op)

    fun update(model: E, id: ID): Int =
        repository.update(model, id)

    fun save(model: E, getID: E.() -> ID?): E =
        repository.save(model, getID)

    fun delete(id: ID): Int =
        repository.delete(id)

    fun count(op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }): Long =
        repository.count(op)

    fun exists(id: ID): Boolean =
        repository.exists(id)

    fun exists(op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }): Boolean =
        repository.exists(op)
}
