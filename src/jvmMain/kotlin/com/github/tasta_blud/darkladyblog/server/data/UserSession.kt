package com.github.tasta_blud.darkladyblog.server.data

import com.github.tasta_blud.darkladyblog.common.data.UserInfo

data class UserSession(val state: String = "", val token: String = "", val user: UserInfo? = null)
