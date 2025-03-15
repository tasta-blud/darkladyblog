package darkladyblog.darkladyblog.server.data

import darkladyblog.darkladyblog.common.base.IdModel

interface ModelPreProcessor<ID : Any, E : IdModel<ID>> : ModelProcessor<ID, E>