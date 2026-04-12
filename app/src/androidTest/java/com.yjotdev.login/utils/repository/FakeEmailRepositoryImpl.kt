package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.EmailModel
import com.yjotdev.login.domain.repository.EmailRepository

@Singleton
class FakeEmailRepositoryImpl @Inject constructor() : EmailRepository {

    override suspend fun sendEmail(email: EmailModel): Result<Unit> {
        return if(email != EmailModel()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al enviar el email"))
        }
    }
}