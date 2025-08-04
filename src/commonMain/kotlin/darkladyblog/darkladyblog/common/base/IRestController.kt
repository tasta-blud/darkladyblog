package darkladyblog.darkladyblog.common.base

import darkladyblog.darkladyblog.common.data.Sorting
import org.koin.core.component.KoinComponent

interface IRestController<ID : Any, E : IdModel<ID>> : KoinComponent {

    suspend fun search(query: String, offset: Long?, limit: Int?, order: Array<Sorting>): List<E>

    suspend fun all(offset: Long?, limit: Int?, order: Array<Sorting>): List<E>

    suspend fun get(id: ID): Result<E>

    suspend fun create(model: E): ID

    suspend fun update(model: E, id: ID): Int

    suspend fun save(model: E, getID: E.() -> ID?): E

    suspend fun delete(id: ID): Int

    suspend fun count(): Long

    suspend fun exists(id: ID): Boolean

}