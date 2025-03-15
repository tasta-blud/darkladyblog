package darkladyblog.darkladyblog.server.entities

import darkladyblog.darkladyblog.server.data.DescribedEntity
import darkladyblog.darkladyblog.server.data.ModifiedEntity
import darkladyblog.darkladyblog.server.db.Comments
import darkladyblog.darkladyblog.server.db.Users
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.ULongEntity
import org.jetbrains.exposed.dao.ULongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CommentEntity(id: EntityID<ULong>) :
    ULongEntity(id), DescribedEntity<ULong>, ModifiedEntity<ULong> {
    companion object : ULongEntityClass<CommentEntity>(Users)

    var topic: TopicEntity by TopicEntity referencedOn Comments.topic
    var parent: CommentEntity? by CommentEntity optionalReferencedOn Comments.parent
    override var title: String by Comments.title
    override var descriptionShortSource: String by Comments.descriptionShortSource
    override var descriptionShortCompiled: String by Comments.descriptionShortCompiled
    override var descriptionLongSource: String by Comments.descriptionLongSource
    override var descriptionLongCompiled: String by Comments.descriptionLongCompiled
    override var createdBy: UserEntity by UserEntity referencedOn Comments.createdBy
    override var createdAt: LocalDateTime by Comments.createdAt
    override var updatedBy: UserEntity? by UserEntity optionalReferencedOn Comments.updatedBy
    override var updatedAt: LocalDateTime? by Comments.updatedAt
}
