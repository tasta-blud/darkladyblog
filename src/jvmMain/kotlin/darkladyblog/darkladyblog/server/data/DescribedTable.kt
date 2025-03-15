package darkladyblog.darkladyblog.server.data

import org.jetbrains.exposed.sql.Column

interface DescribedTable<ID : Any> : IdentifiedTable<ID> {
    val title: Column<String>
    val descriptionShortSource: Column<String>
    val descriptionShortCompiled: Column<String>
    val descriptionLongSource: Column<String>
    val descriptionLongCompiled: Column<String>
}