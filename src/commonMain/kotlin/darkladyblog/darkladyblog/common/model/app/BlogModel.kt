package darkladyblog.darkladyblog.common.model.app

import darkladyblog.darkladyblog.common.base.Aliased
import darkladyblog.darkladyblog.common.base.Described
import darkladyblog.darkladyblog.common.base.Modified
import darkladyblog.darkladyblog.common.base.ULongIdModel
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.util.now
import darkladyblog.darkladyblog.common.util.toAlias
import darkladyblog.darkladyblog.common.util.toLocalizedString
import dev.fritz2.core.Lenses
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class BlogModel(
    override val title: String,
    override val descriptionShortSource: String,
    override val descriptionShortCompiled: String,
    override val descriptionLongSource: String,
    override val descriptionLongCompiled: String,
    override val alias: String = title.toAlias(),
    override val createdBy: UserModel,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val createdAtString: String = createdAt.toLocalizedString(),
    override val updatedBy: UserModel? = null,
    override val updatedAt: LocalDateTime? = null,
    override val updatedAtString: String = updatedAt?.toLocalizedString() ?: "",
    override val id: ULong? = null,
) : ULongIdModel, Aliased<ULong>, Described<ULong>, Modified<ULong> {

    fun withUserAndId(userModel: UserModel, id: ULong?): BlogModel =
        copy(createdBy = userModel, id = id)

    companion object {
        val NULL_BLOG: BlogModel =
            BlogModel("", "", "", "", "", "", UserModel.NULL_USER)
    }
}
