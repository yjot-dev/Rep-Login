package com.yjotdev.login.domain.usecase.payment

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
import com.yjotdev.login.domain.repository.PaymentRepository

class CaptureOrderUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    /** Capturar orden de pago mediante caso de uso **/
    suspend operator fun invoke(orderId: CaptureOrderRequestModel): Result<Unit> {
        return paymentRepository.captureOrder(orderId)
    }
}