package darkladyblog.darkladyblog.server.data

interface AliasedEntity<ID : Any> : OwnedEntity<ID> {
    var alias: String
}