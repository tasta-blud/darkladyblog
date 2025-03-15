package darkladyblog.darkladyblog.server.data

interface DescribedEntity<ID : Any> : OwnedEntity<ID> {
    var title: String
    val descriptionShortSource: String
    val descriptionShortCompiled: String
    val descriptionLongSource: String
    val descriptionLongCompiled: String
}