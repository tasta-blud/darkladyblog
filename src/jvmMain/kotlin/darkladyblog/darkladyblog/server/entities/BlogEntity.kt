package darkladyblog.darkladyblog.server.entities

import darkladyblog.darkladyblog.server.data.AliasedEntity
import darkladyblog.darkladyblog.server.data.DescribedEntity
import darkladyblog.darkladyblog.server.data.ModifiedEntity
import darkladyblog.darkladyblog.server.db.Blogs
import darkladyblog.darkladyblog.server.db.Users
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.ULongEntity
import org.jetbrains.exposed.dao.ULongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BlogEntity(id: EntityID<ULong>) :
    ULongEntity(id), AliasedEntity<ULong>, DescribedEntity<ULong>, ModifiedEntity<ULong> {
    companion object : ULongEntityClass<BlogEntity>(Users)

    override var title: String by Blogs.title
    override var descriptionShortSource: String by Blogs.descriptionShortSource
    override var descriptionShortCompiled: String by Blogs.descriptionShortCompiled
    override var descriptionLongSource: String by Blogs.descriptionLongSource
    override var descriptionLongCompiled: String by Blogs.descriptionLongCompiled
    override var alias: String by Blogs.alias
    override var createdBy: UserEntity by UserEntity referencedOn Blogs.createdBy
    override var createdAt: LocalDateTime by Blogs.createdAt
    override var updatedBy: UserEntity? by UserEntity optionalReferencedOn Blogs.updatedBy
    override var updatedAt: LocalDateTime? by Blogs.updatedAt
}
