package darkladyblog.darkladyblog.common.base

interface Described<ID : Any> : IdModel<ID> {
    val title: String
    val descriptionShortSource: String
    val descriptionShortCompiled: String
    val descriptionLongSource: String
    val descriptionLongCompiled: String
}