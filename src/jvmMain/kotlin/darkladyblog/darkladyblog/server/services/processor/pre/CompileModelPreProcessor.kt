package darkladyblog.darkladyblog.server.services.processor.pre

import darkladyblog.darkladyblog.common.base.Modified
import darkladyblog.darkladyblog.common.services.DescriptionCompiler
import darkladyblog.darkladyblog.common.services.DescriptionShortener
import darkladyblog.darkladyblog.server.data.ModelPreProcessor
import org.koin.core.component.inject

abstract class CompileModelPreProcessor<ID : Any, M : Modified<ID>>() : ModelPreProcessor<ID, M> {
    val shortener: DescriptionShortener by inject()
    val compiler: DescriptionCompiler by inject()
}