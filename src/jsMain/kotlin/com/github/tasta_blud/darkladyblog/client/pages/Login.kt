package com.github.tasta_blud.darkladyblog.client.pages

import com.github.tasta_blud.darkladyblog.client.components.page.Page
import com.github.tasta_blud.darkladyblog.client.components.page.Pages
import com.github.tasta_blud.darkladyblog.client.controllers.AppController
import com.github.tasta_blud.darkladyblog.common.data.LoginFormData
import io.kvision.core.onClickLaunch
import io.kvision.form.FormEnctype
import io.kvision.form.FormMethod
import io.kvision.form.check.CheckBox
import io.kvision.form.formPanel
import io.kvision.form.text.Password
import io.kvision.form.text.Text
import io.kvision.html.*
import io.kvision.i18n.gettext

class Login(title: String = "", route: String? = null) : Page(title, route, "index") {
    init {
        main(className = "form col-4 m-auto") {
            formPanel<LoginFormData>(
                method = FormMethod.POST,
                action = null,
                enctype = FormEnctype.MULTIPART,
                type = null,
                condensed = true,
                className = "",
            ) {
                h1(className = "h3 mb-3 fw-normal") { +gettext("Please sign in") }
                div(className = "form-floating") {
                    add(LoginFormData::username, Text(type = InputType.TEXT) { placeholder = gettext("Username") })
                }
                div(className = "form-floating") {
                    add(LoginFormData::password, Password { placeholder = gettext("Password") })
                }
                div(className = "form-check text-start my-3") {
                    add(LoginFormData::remember, CheckBox(label = gettext("Remember me")))
                }
                button(className = "btn btn-primary w-100 py-2", type = ButtonType.BUTTON, text = gettext("Sign in")) {
                    onClickLaunch {
                        AppController.login(getData())
                    }
                }
                p(className = "mt-5 mb-3 text-body-secondary") { +"© 2017–2024" }
            }


        }
    }

}

fun Pages.login(title: String = "", route: String? = null): Login =
    Login(title, route).also { add(it, route) }
