package darkladyblog.darkladyblog.server.services.app

import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.server.base.RepositoryService
import darkladyblog.darkladyblog.server.db.Comments
import darkladyblog.darkladyblog.server.repositories.CommentRepository
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SortOrder
import org.koin.core.annotation.Single

@Single
class CommentRepositoryService(commentRepository: CommentRepository) :
    RepositoryService<Comments, ULong, CommentModel, CommentRepository>(commentRepository) {

    fun all(
        topicId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(repository.alias[repository.table.id] to SortOrder.ASC),
    ): List<CommentModel> =
        repository.all(topicId, offset, limit, *order)

    fun all(
        topicId: ULong,
        commentId: ULong? = null,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(repository.alias[repository.table.id] to SortOrder.ASC),
    ): List<CommentModel> =
        repository.all(topicId, commentId, offset, limit, *order)

    fun count(
        topicId: ULong,
    ): Long =
        repository.count(topicId)

    fun count(
        topicId: ULong,
        commentId: ULong? = null,
    ): Long =
        repository.count(topicId, commentId)

}
