package com.yjotdev.login.domain.usecase.payment

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentDaoRepository

class GetPaymentsByUserIdUseCase @Inject constructor(
    private val paymentDaoRepository: PaymentDaoRepository
) {
    operator fun invoke(userId: Int, limit: Int): Flow<List<PaymentModel>> {
        return paymentDaoRepository.getPaymentsByUserId(userId, limit)
    }
}