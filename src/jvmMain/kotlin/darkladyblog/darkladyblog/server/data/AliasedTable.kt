package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.sql.Column

interface AliasedTable<ID : Any> : IdentifiedTable<ID> {
    val alias: Column<String>
}