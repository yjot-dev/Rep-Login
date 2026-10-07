package com.yjotdev.login.domain.usecase.payment

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentApiRepository

class FindPaymentsUseCase @Inject constructor(
    private val paymentApiRepository: PaymentApiRepository
) {
    /** Selecciona todos los pagos del usuario mediante caso de uso **/
    suspend operator fun invoke(userId: Int): Result<List<PaymentModel>> {
        return paymentApiRepository.selectPayments(userId)
    }
}