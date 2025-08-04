package darkladyblog.darkladyblog.client.base.rest

import darkladyblog.darkladyblog.client.intercept.runWithToastAsync
import darkladyblog.darkladyblog.common.base.IRestController
import darkladyblog.darkladyblog.common.base.IdModel
import darkladyblog.darkladyblog.common.data.ColumnName
import darkladyblog.darkladyblog.common.data.SortDirection
import darkladyblog.darkladyblog.common.data.Sorting

abstract class RestService<ID : Any, E : IdModel<ID>, C : IRestController<ID, E>>(val controller: C) {

    suspend fun count(): Long? =
        runWithToastAsync { controller.count() }.getOrNull()


    suspend fun exists(id: ID): Boolean? =
        runWithToastAsync { controller.exists(id) }.getOrNull()

    suspend fun get(id: ID): E? =
        runWithToastAsync { controller.get(id).getOrNull() }.getOrNull()

    suspend fun all(
        offset: Long? = null,
        limit: Int? = null,
        order: Array<Sorting> = arrayOf(Sorting(ColumnName("id"), SortDirection.ASC))
    ): List<E>? =
        runWithToastAsync { controller.all(offset, limit, order) }.getOrNull()

    suspend fun create(model: E): ID? =
        runWithToastAsync { controller.create(model) }.getOrNull()

    suspend fun update(model: E, id: ID): E? =
        runWithToastAsync { controller.update(model, id) }.map { model }.getOrNull()

    suspend fun save(model: E): E? =
        runWithToastAsync { controller.save(model) { model.id } }.getOrNull()

    suspend fun delete(id: ID): Int? =
        runWithToastAsync { controller.delete(id) }.getOrNull()
}