package com.yjotdev.login.domain.usecase.payment

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentRepository

class SelectPaymentsUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    /** Selecciona todos los pagos del usuario mediante caso de uso **/
    suspend operator fun invoke(userId: Int, maxRows: Int? = null): Result<List<PaymentModel>> {
        return paymentRepository.selectPayments(userId, maxRows)
    }
}