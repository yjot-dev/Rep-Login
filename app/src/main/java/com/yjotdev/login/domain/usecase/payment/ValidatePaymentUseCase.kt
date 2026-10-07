package com.yjotdev.login.domain.usecase.payment

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.ValidateModel
import com.yjotdev.login.domain.repository.PaymentApiRepository

class ValidatePaymentUseCase @Inject constructor(
    private val paymentApiRepository: PaymentApiRepository
) {
    suspend operator fun invoke(validate: ValidateModel): Result<Unit> {
        return paymentApiRepository.validatePayment(validate)
    }
}