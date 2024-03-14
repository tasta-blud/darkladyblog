package com.github.tasta_blud.darkladyblog.server.services

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.koin.java.KoinJavaComponent.inject

abstract class Repository<ID, TBL : IdTable<ID>, E : Any>(protected val table: TBL) where ID : Any, ID : Comparable<ID> {

    protected val db: DB by inject(DB::class.java)

    protected abstract fun TBL.update(it: UpdateBuilder<*>, entity: E)

    protected abstract fun TBL.transform(row: ResultRow): E

    fun create(insert: TBL.(InsertStatement<EntityID<ID>>) -> Unit): ID =
        db.execute { table.insertAndGetId { table.insert(it) }.value }

    fun create(entity: E): ID =
        create { table.update(it, entity) }

    fun update(where: SqlExpressionBuilder.() -> Op<Boolean>, update: TBL.(UpdateStatement) -> Unit): Int =
        db.execute { table.update(where) { table.update(it) } }

    fun update(id: ID, entity: E): Int =
        update({ table.id eq id }) { table.update(it, entity) }

    fun findAll(
        query: Query.() -> Query = { this },
        where: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }
    ): List<E> =
        db.execute { table.selectAll().where(where).orderBy(table.id).query().map { table.transform(it) } }

    fun findAll(
        query: Query.() -> Query = { this },
        offset: Long,
        limit: Int,
        where: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }
    ): List<E> =
        findAll({ query().limit(limit, offset) }, where)

    fun findOne(
        query: Query.() -> Query = { this },
        where: SqlExpressionBuilder.() -> Op<Boolean> = { Op.TRUE }
    ): E? =
        findAll({ query().limit(1, 0) }, where).firstOrNull()

    fun delete(where: TBL.(ISqlExpressionBuilder) -> Op<Boolean>): Int =
        db.execute { table.deleteWhere(op = { table.where(it) }) }

    fun delete(id: ID): Int =
        delete { table.id eq id }
}
