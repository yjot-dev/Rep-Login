package com.yjotdev.login.domain.port

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.EmailEntity

interface EmailPort {
    /**
     * Envia un correo electrónico a un usuario
     * @return Result<UserEntity> que contiene el usuario si se encuentra, o un error.
     */
    suspend fun sendEmail(email: EmailEntity): Result<Unit>
}