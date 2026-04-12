package com.yjotdev.login.domain.usecase.email

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.EmailModel
import com.yjotdev.login.domain.repository.EmailRepository

class SendEmailUseCase @Inject constructor(
    private val emailRepository: EmailRepository
) {
    /** Enviar email mediante caso de uso **/
    suspend operator fun invoke(email: EmailModel): Result<Unit> {
        return emailRepository.sendEmail(email)
    }
}