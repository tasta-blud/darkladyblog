package darkladyblog.darkladyblog.server.data

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

interface ModifiedTable<ID : Any> : OwnedTable<ID> {
    override val createdBy: Column<EntityID<ID>>
    val createdAt: Column<LocalDateTime>
    val updatedBy: Column<EntityID<ID>?>
    val updatedAt: Column<LocalDateTime?>
}