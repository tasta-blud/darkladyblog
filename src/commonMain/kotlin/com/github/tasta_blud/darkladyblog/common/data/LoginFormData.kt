package com.github.tasta_blud.darkladyblog.common.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginFormData(
    val username: String,
    val password: String,
    val remember: Boolean,
)
