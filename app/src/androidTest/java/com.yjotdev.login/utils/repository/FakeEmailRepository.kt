package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.EmailEntity
import com.yjotdev.login.domain.port.EmailPort

@Singleton
class FakeEmailRepository @Inject constructor() : EmailPort {
    override suspend fun sendEmail(email: EmailEntity): Result<Unit> {
        return if(email != EmailEntity()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al enviar el email"))
        }
    }
}