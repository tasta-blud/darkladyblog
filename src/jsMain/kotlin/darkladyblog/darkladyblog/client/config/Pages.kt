package darkladyblog.darkladyblog.client.config

import darkladyblog.darkladyblog.client.base.route.Page
import darkladyblog.darkladyblog.client.errorPages.notFoundPage
import darkladyblog.darkladyblog.client.pages.pageBlog
import darkladyblog.darkladyblog.client.pages.pageBlogs
import darkladyblog.darkladyblog.client.pages.pageIndex
import darkladyblog.darkladyblog.client.pages.pageLogin
import darkladyblog.darkladyblog.client.pages.pageProfile
import darkladyblog.darkladyblog.client.pages.pageRegistration
import darkladyblog.darkladyblog.client.pages.pageSearch
import darkladyblog.darkladyblog.client.pages.pageTopic
import darkladyblog.darkladyblog.i18n.ApplicationTranslations
import darkladyblog.darkladyblog.i18n.CommonTranslations
import darkladyblog.darkladyblog.i18n.ErrorTranslations
import darkladyblog.darkladyblog.i18n.LoginTranslations
import darkladyblog.darkladyblog.i18n.RegistrationTranslations
import darkladyblog.darkladyblog.i18n.SearchTranslations

enum class Pages(val page: Page) {

    PAGE_NOT_FOUND(
        Page(
            page = "404",
            title = { ErrorTranslations.not_found_title() },
            isSpecial = true,
            content = { notFoundPage(it) })
    ),
    PAGE_DEFAULT(
        Page(
            page = "",
            title = { "" },
            content = { pageIndex(it) }
        )
    ),
    PAGE_LOGIN(
        Page(
            page = "login",
            title = { LoginTranslations.login() },
            isSpecial = true,
            content = { pageLogin(it) }
        )
    ),

    PAGE_BLOGS(
        Page(
            page = "blogs",
            title = { ApplicationTranslations.application_title() },
            content = { pageBlogs(it) }
        )
    ),
    PAGE_BLOG(
        Page(
            page = "blog",
            title = { CommonTranslations.blog_w(it.getOrNull(0) ?: "") },
            isSpecial = true,
            content = { pageBlog(it) }
        )
    ),
    PAGE_TOPIC(
        Page(
            page = "topic",
            title = { CommonTranslations.topic_w(it.getOrNull(0) ?: "") },
            isSpecial = true,
            content = { pageTopic(it) }
        )
    ),
    PAGE_PROFILE(
        Page(
            page = "profile",
            title = { it.getOrNull(0) ?: "" },
            isSpecial = true,
            content = { pageProfile(it) }
        )
    ),
    PAGE_REGISTRATION(
        Page(
            page = "registration",
            title = { RegistrationTranslations.registration() },
            isSpecial = true,
            content = { pageRegistration(it) }
        )
    ),
    PAGE_SEARCH(
        Page(
            page = "search",
            title = { SearchTranslations.title() },
            isSpecial = true,
            content = { pageSearch(it) }
        )
    ),
}
