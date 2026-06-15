package com.yjotdev.login.domain.usecase.payment

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.repository.PaymentRepository

class CreateOrderUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    /** Crear orden de pago mediante caso de uso **/
    suspend operator fun invoke(body: CreateOrderRequestModel): Result<CreateOrderResultModel> {
        return paymentRepository.createOrder(body)
    }
}