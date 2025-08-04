package darkladyblog.darkladyblog.common.ex

import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.annotations.RpcServiceException

@RpcServiceException
class NoEntityException(override val message: String) : AbstractServiceException()