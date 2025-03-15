package darkladyblog.darkladyblog.server.data

import darkladyblog.darkladyblog.common.base.IdModel

interface ModelPostProcessor<ID : Any, E : IdModel<ID>> : ModelProcessor<ID, E>