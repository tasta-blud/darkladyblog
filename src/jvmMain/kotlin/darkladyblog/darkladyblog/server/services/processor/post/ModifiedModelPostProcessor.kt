package darkladyblog.darkladyblog.server.services.processor.post

import darkladyblog.darkladyblog.common.base.Modified
import darkladyblog.darkladyblog.server.data.ModelPostProcessor
import org.koin.core.component.KoinComponent

abstract class ModifiedModelPostProcessor<ID : Any, M : Modified<ID>>() : ModelPostProcessor<ID, M>, KoinComponent