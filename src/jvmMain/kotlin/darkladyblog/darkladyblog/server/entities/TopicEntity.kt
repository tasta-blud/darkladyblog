package darkladyblog.darkladyblog.server.entities

import darkladyblog.darkladyblog.server.data.AliasedEntity
import darkladyblog.darkladyblog.server.data.DescribedEntity
import darkladyblog.darkladyblog.server.data.ModifiedEntity
import darkladyblog.darkladyblog.server.db.Topics
import darkladyblog.darkladyblog.server.db.Users
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.ULongEntity
import org.jetbrains.exposed.dao.ULongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TopicEntity(id: EntityID<ULong>) :
    ULongEntity(id), AliasedEntity<ULong>, DescribedEntity<ULong>, ModifiedEntity<ULong> {
    companion object : ULongEntityClass<TopicEntity>(Users)

    var blog: TopicEntity by TopicEntity referencedOn Topics.blog
    override var title: String by Topics.title
    override var descriptionShortSource: String by Topics.descriptionShortSource
    override var descriptionShortCompiled: String by Topics.descriptionShortCompiled
    override var descriptionLongSource: String by Topics.descriptionLongSource
    override var descriptionLongCompiled: String by Topics.descriptionLongCompiled
    override var alias: String by Topics.alias
    override var createdBy: UserEntity by UserEntity referencedOn Topics.createdBy
    override var createdAt: LocalDateTime by Topics.createdAt
    override var updatedBy: UserEntity? by UserEntity optionalReferencedOn Topics.updatedBy
    override var updatedAt: LocalDateTime? by Topics.updatedAt
}
