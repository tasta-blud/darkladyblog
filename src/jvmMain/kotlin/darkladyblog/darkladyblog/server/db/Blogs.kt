package darkladyblog.darkladyblog.server.db

import darkladyblog.darkladyblog.common.util.now
import darkladyblog.darkladyblog.server.data.AliasedTable
import darkladyblog.darkladyblog.server.data.DescribedTable
import darkladyblog.darkladyblog.server.data.ModifiedTable
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.ULongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Blogs : ULongIdTable(), AliasedTable<ULong>, DescribedTable<ULong>, ModifiedTable<ULong> {
    override val title: Column<String> = varchar("title", length = 255)
    override val descriptionShortSource: Column<String> = varchar("description_short_source", length = 255)
    override val descriptionShortCompiled: Column<String> = varchar("description_short_compiled", length = 255)
    override val descriptionLongSource: Column<String> = text("description_long_source", eagerLoading = true)
    override val descriptionLongCompiled: Column<String> = text("description_long_compiled", eagerLoading = true)
    override val alias: Column<String> = varchar("alias", length = 255).uniqueIndex()
    override val createdBy: Column<EntityID<ULong>> = reference("created_by", Users)
    override val createdAt: Column<LocalDateTime> = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val updatedBy: Column<EntityID<ULong>?> = optReference("updated_by", Users)
    override val updatedAt: Column<LocalDateTime?> = datetime("updated_at").nullable()
}

