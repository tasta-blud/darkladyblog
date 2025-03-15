package darkladyblog.darkladyblog.server.base

import darkladyblog.darkladyblog.server.services.DB
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

abstract class Repository<TBL : IdTable<ID>, ID : Any, E : Any>(
    val table: TBL,
    val alias: Alias<TBL> = Alias(table, "table")
) : KoinComponent {
    protected val db: DB = getKoin().get()

    internal abstract fun fromRow(row: ResultRow, table: TBL, alias: Alias<TBL>): E

    internal abstract fun updating(table: TBL, it: UpdateBuilder<Int>, model: E)

    fun all(
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(alias[table.id] to SortOrder.ASC),
        op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }
    ): List<E> =
        db.transactionally {
            selectRows(table, alias)
                .where(op)
                .orderBy(*(order))
                .let { if (offset != null) it.offset(offset) else it }
                .let { if (limit != null) it.limit(limit) else it }
                .map { fromRow(it, table, alias) }
        }

    protected open fun selectRows(table: TBL, alias: Alias<TBL>): Query =
        alias.selectAll()

    fun get(op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }): E? =
        all(0, 1, op = op).singleOrNull()

    fun get(id: ID): E? =
        get { alias[table.id] eq id }

    fun create(model: E): ID =
        db.transactionally {
            table.insertAndGetId { updating(table, it, model) }.value
        }

    fun createReturning(model: E, copy: E.(ID) -> E): E =
        create(model).let { model.copy(it) }

    fun update(model: E, op: SqlExpressionBuilder.() -> Op<Boolean>): Int =
        db.transactionally {
            table.update(op, limit = 1) { updating(table, it, model) }
        }

    fun update(model: E, id: ID): Int =
        update(model) { table.id eq id }

    fun save(model: E, getID: E.() -> ID?): E {
        val id: ID? = model.getID()
        if (id == null) {
            return get(create(model))!!
        } else {
            update(model, id)
            return model
        }
    }

    fun delete(id: ID): Int =
        db.transactionally {
            table.deleteWhere(1) { table.id eq id }
        }

    fun count(op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }): Long =
        db.transactionally {
            alias.select(alias[table.id]).where(op).count()
        }

    fun exists(id: ID): Boolean =
        db.transactionally {
            alias.select(alias[table.id]).where { table.id eq id }.limit(1).singleOrNull()
        } != null

    fun exists(op: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }): Boolean =
        db.transactionally {
            alias.select(alias[table.id]).where(op).count()
        } != 0L
}
