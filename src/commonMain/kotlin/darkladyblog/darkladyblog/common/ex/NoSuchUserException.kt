package darkladyblog.darkladyblog.common.ex

import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.annotations.RpcServiceException

@RpcServiceException
class NoSuchUserException(override val message: String) : AbstractServiceException()