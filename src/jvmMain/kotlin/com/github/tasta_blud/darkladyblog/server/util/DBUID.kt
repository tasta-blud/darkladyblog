package com.github.tasta_blud.darkladyblog.server.util

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.clientDefault
import org.jetbrains.exposed.sql.UUIDColumnType
import java.io.Serializable
import java.util.*

abstract class TypedId(open val id: UUID) : Serializable, Comparable<TypedId> {
    override fun compareTo(other: TypedId): Int = this.id.compareTo(other.id)
}

class TypedUUIDColumnType<T : TypedId>(
    val constructor: (UUID) -> T,
    private val uuidColType: UUIDColumnType = UUIDColumnType()
) : IColumnType by uuidColType {
    override fun valueFromDB(value: Any): T = constructor(uuidColType.valueFromDB(value))
    override fun notNullValueToDB(value: Any): Any = uuidColType.notNullValueToDB(valueUnwrap(value))
    override fun nonNullValueToString(value: Any): String = uuidColType.nonNullValueToString(valueUnwrap(value))
    private fun valueUnwrap(value: Any) = (value as? TypedId)?.id ?: value
}

fun <T : TypedId> Table.typedUuid(name: String, constructor: (UUID) -> T) =
    registerColumn<T>(name, TypedUUIDColumnType<T>(constructor))

fun <T : TypedId> Column<T>.autoGenerate(constructor: (UUID) -> T): Column<T> =
    clientDefault { constructor(UUID.randomUUID()) }
