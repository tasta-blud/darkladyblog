package darkladyblog.darkladyblog.common.base

interface Aliased<ID : Any> : IdModel<ID> {
    val alias: String
}