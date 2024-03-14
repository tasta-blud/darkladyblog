package com.github.tasta_blud.darkladyblog.common.services

import com.github.tasta_blud.darkladyblog.common.data.LoginFormData
import com.github.tasta_blud.darkladyblog.common.data.UserInfo
import io.kvision.annotations.KVService

@KVService
interface ILoginService {
    suspend fun ping(message: String): String
    suspend fun login(formData: LoginFormData): Result<UserInfo>
    suspend fun logout(): Result<Unit>
}
