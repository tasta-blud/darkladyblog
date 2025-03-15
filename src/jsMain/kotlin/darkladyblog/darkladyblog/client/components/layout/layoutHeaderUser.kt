package darkladyblog.darkladyblog.client.components.layout

import darkladyblog.darkladyblog.client.config.Pages
import darkladyblog.darkladyblog.client.store.LoginStore
import darkladyblog.darkladyblog.client.store.PrincipalStore
import darkladyblog.darkladyblog.client.util.navigates
import darkladyblog.darkladyblog.common.util.getGravatarURL
import darkladyblog.darkladyblog.i18n.HeaderMenuTranslations
import dev.fritz2.core.RenderContext
import dev.fritz2.core.id
import dev.fritz2.core.src

fun RenderContext.layoutHeaderUser() {

    ul("navbar-nav ms-auto me-0 mb-2 mb-lg-0") {
        PrincipalStore.renderIfNotLogged(this) {
            li("nav-item") {
                a("nav-link") {
                    attr("aria-current", "page")
                    +HeaderMenuTranslations.register()
                    navigates(Pages.PAGE_REGISTRATION.page.withParameters())
                }
            }
            li("nav-item") {
                a("nav-link active") {
                    attr("aria-current", "page")
                    +HeaderMenuTranslations.login()
                    navigates(Pages.PAGE_LOGIN.page.withParameters())
                }
            }
        }
        PrincipalStore.renderIfLogged(this) {
            li("nav-item dropdown") {
                a("nav-link dropdown-toggle") {
                    id("navbarDropdownUser")
                    attr("role", "button")
                    attr("data-bs-toggle", "dropdown")
                    attr("aria-expanded", "false")
                    img { src(getGravatarURL(it.email, 20)) }
                    +" ${it.username}"
                }
                ul("dropdown-menu") {
                    attr("aria-labelledby", "navbarDropdownUser")
                    li {
                        a("dropdown-item") {
                            +HeaderMenuTranslations.profile()
                            navigates(Pages.PAGE_PROFILE.page.withParameters())
                        }
                    }
                    li {
                        hr("dropdown-divider") {}
                    }
                    li {
                        a("dropdown-item") {
                            +HeaderMenuTranslations.logout()
                            navigates(LoginStore.logout, Pages.PAGE_DEFAULT.page.withParameters())
                        }
                    }
                }
            }
        }
    }
}
