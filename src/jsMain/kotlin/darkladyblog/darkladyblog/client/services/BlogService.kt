package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.common.controllers.IBlogRestController
import darkladyblog.darkladyblog.common.model.app.BlogModel
import dev.kilua.rpc.getService

object BlogService : RestService<ULong, BlogModel, IBlogRestController>(controller = getService<IBlogRestController>())