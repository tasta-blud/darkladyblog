package darkladyblog.darkladyblog.client.services

import darkladyblog.darkladyblog.client.base.rest.RestService
import darkladyblog.darkladyblog.common.model.app.BlogModel
import kotlinx.serialization.builtins.serializer

object BlogService : RestService<ULong, BlogModel>(ULong.serializer(), BlogModel.serializer(), "/blogs")