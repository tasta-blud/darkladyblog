@file:Suppress("D")

package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.common.model.Sex
import darkladyblog.darkladyblog.common.model.UserModel
import darkladyblog.darkladyblog.common.model.app.BlogModel
import darkladyblog.darkladyblog.common.model.app.CommentModel
import darkladyblog.darkladyblog.common.model.app.TopicModel
import darkladyblog.darkladyblog.server.services.UserRepositoryService
import darkladyblog.darkladyblog.server.services.app.BlogRepositoryService
import darkladyblog.darkladyblog.server.services.app.CommentRepositoryService
import darkladyblog.darkladyblog.server.services.app.TopicRepositoryService
import io.ktor.server.application.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun Application.testData() {
    if (!developmentMode) return
    GlobalScope.launch {
        val userService: UserRepositoryService by inject()
        val userModel: UserModel =
            if (userService.getByUsername("user") == null) {
                UserModel(
                    "user",
                    userService.defaultPasswordEncoder.encode("user"),
                    "User",
                    "user@localhost",
                    Sex.MALE
                ).let { model -> userService.createReturning(model) { id -> model.copy(id = id) } }
            } else {
                userService.getByUsername("user")!!
            }
        val blogRepositoryService: BlogRepositoryService by inject()
        val topicRepositoryService: TopicRepositoryService by inject()
        val commentRepositoryService: CommentRepositoryService by inject()
        if (blogRepositoryService.count() == 0L) {
            for (b in 0..3) {
                val blogModel = async {
                    BlogModel(
                        "Blog$b",
                        "Blog $b",
                        "Blog $b",
                        "Blog $b",
                        "Blog $b",
                        createdBy = userModel
                    )
                        .let { model -> blogRepositoryService.createReturning(model) { id -> model.copy(id = id) } }
                }.await()
                launch {
                    for (t in 0..100) {
                        val topicModel = async {
                            TopicModel(
                                blogModel, "Topic$b$t",
                                "Topic $b $t",
                                "Topic $b $t",
                                "Topic $b $t",
                                "Topic $b $t",
                                createdBy = userModel
                            )
                                .let { model -> topicRepositoryService.createReturning(model) { id -> model.copy(id = id) } }
                        }.await()
                        launch {
                            for (c in 0..100) {
                                val commentModel = async {
                                    CommentModel(
                                        topicModel,
                                        null,
                                        "Comment$b$t$c",
                                        "Comment $b $t $c",
                                        "Comment $b $t $c",
                                        "Comment $b $t $c",
                                        "Comment $b $t $c",
                                        createdBy = userModel
                                    ).let { model ->
                                        commentRepositoryService.createReturning(model) { id ->
                                            model.copy(
                                                id = id
                                            )
                                        }
                                    }
                                }.await()
                                launch {
                                    for (c1 in 0..10) {
                                        async {
                                            CommentModel(
                                                topicModel,
                                                commentModel,
                                                "Comment$b$t$c$c1",
                                                "Comment $b $t $c $c1",
                                                "Comment $b $t $c $c1",
                                                "Comment $b $t $c $c1",
                                                "Comment $b $t $c $c1",
                                                createdBy = userModel
                                            ).let { model ->
                                                commentRepositoryService.createReturning(model) { id ->
                                                    model.copy(
                                                        id = id
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
