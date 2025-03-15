package darkladyblog.darkladyblog.server.services.app

import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.server.base.RepositoryService
import darkladyblog.darkladyblog.server.db.Blogs
import darkladyblog.darkladyblog.server.repositories.BlogRepository
import org.koin.core.annotation.Single

@Single
class BlogRepositoryService(blogRepository: BlogRepository) :
    RepositoryService<Blogs, ULong, BlogModel, BlogRepository>(blogRepository)
