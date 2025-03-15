package darkladyblog.darkladyblog.server.services.app

import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.server.base.RepositoryService
import darkladyblog.darkladyblog.server.db.Topics
import darkladyblog.darkladyblog.server.repositories.TopicRepository
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SortOrder
import org.koin.core.annotation.Single

@Single
class TopicRepositoryService(topicRepository: TopicRepository) :
    RepositoryService<Topics, ULong, TopicModel, TopicRepository>(topicRepository) {
    fun all(
        blogId: ULong,
        offset: Long? = null,
        limit: Int? = null,
        vararg order: Pair<Column<*>, SortOrder> = arrayOf(repository.alias[repository.table.id] to SortOrder.ASC),
    ): List<TopicModel> =
        repository.all(blogId, offset, limit, *order)

    fun count(
        blogId: ULong,
    ): Long =
        repository.count(blogId)
}
