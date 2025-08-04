package darkladyblog.darkladyblog.common.model.app.search

import darkladyblog.darkladyblog.common.base.Aliased
import darkladyblog.darkladyblog.common.base.Described
import darkladyblog.darkladyblog.common.base.IdModel
import darkladyblog.darkladyblog.common.base.Modified
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.common.util.now
import darkladyblog.darkladyblog.common.util.toAlias
import darkladyblog.darkladyblog.common.util.toLocalizedString
import dev.fritz2.core.Lenses
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class SearchResult(
    val type: SearchResultType,
    override val id: ULong?,
    override val title: String,
    override val descriptionShortSource: String,
    override val descriptionShortCompiled: String,
    override val descriptionLongSource: String,
    override val descriptionLongCompiled: String,
    override val alias: String,
    override val createdBy: UserModel,
    override val createdAt: LocalDateTime,
    override val createdAtString: String,
    override val updatedBy: UserModel?,
    override val updatedAt: LocalDateTime?,
    override val updatedAtString: String,
) : IdModel<ULong>, Aliased<ULong>, Described<ULong>, Modified<ULong> {
    companion object {

        fun toSearchResult(model: BlogModel): SearchResult =
            SearchResult(
                SearchResultType.BLOG,
                model.id,
                model.title,
                model.descriptionShortSource,
                model.descriptionShortCompiled,
                model.descriptionLongSource,
                model.descriptionLongCompiled,
                model.alias,
                model.createdBy,
                model.createdAt,
                model.createdAtString,
                model.updatedBy,
                model.updatedAt,
                model.updatedAtString,
            )

        fun toSearchResult(model: TopicModel): SearchResult =
            SearchResult(
                SearchResultType.TOPIC,
                model.id,
                model.title,
                model.descriptionShortSource,
                model.descriptionShortCompiled,
                model.descriptionLongSource,
                model.descriptionLongCompiled,
                model.alias,
                model.createdBy,
                model.createdAt,
                model.createdAtString,
                model.updatedBy,
                model.updatedAt,
                model.updatedAtString,
            )

        fun toSearchResult(model: CommentModel): SearchResult =
            SearchResult(
                SearchResultType.COMMENT,
                model.id,
                model.title,
                model.descriptionShortSource,
                model.descriptionShortCompiled,
                model.descriptionLongSource,
                model.descriptionLongCompiled,
                model.title.toAlias(),
                model.createdBy,
                model.createdAt,
                model.createdAtString,
                model.updatedBy,
                model.updatedAt,
                model.updatedAtString,
            )

        fun toSearchResult(model: UserModel): SearchResult =
            SearchResult(
                SearchResultType.USER,
                model.id,
                model.title,
                model.descriptionShortSource,
                model.descriptionShortCompiled,
                model.descriptionLongSource,
                model.descriptionLongCompiled,
                model.title.toAlias(),
                UserModel.NULL_USER,
                LocalDateTime.now(),
                LocalDateTime.now().toLocalizedString(),
                UserModel.NULL_USER,
                LocalDateTime.now(),
                LocalDateTime.now().toLocalizedString(),
            )
    }
}