package com.yjotdev.login.domain.usecase.email

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.EmailEntity
import com.yjotdev.login.domain.port.EmailPort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailUseCase @Inject constructor(
    private val emailPort: EmailPort
) {
    /** Enviar email mediante caso de uso **/
    suspend operator fun invoke(email: EmailEntity): Result<Unit> {
        return emailPort.sendEmail(email)
    }
}