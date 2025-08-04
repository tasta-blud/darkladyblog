package darkladyblog.darkladyblog.server.controllers

import darkladyblog.darkladyblog.common.controllers.IBlogRestController
import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.server.base.RestController
import darkladyblog.darkladyblog.server.db.Blogs
import darkladyblog.darkladyblog.server.repositories.BlogRepository
import darkladyblog.darkladyblog.server.services.app.BlogRepositoryService
import io.ktor.server.application.ApplicationCall
import org.koin.core.annotation.Single

@Single
class BlogRestController(blogRepositoryService: BlogRepositoryService, call: ApplicationCall) :
    RestController<Blogs, ULong, BlogModel, BlogRepository, BlogRepositoryService>(blogRepositoryService, call),
    IBlogRestController
