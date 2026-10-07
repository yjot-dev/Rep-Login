package com.yjotdev.login.domain.usecase.payment

import javax.inject.Inject
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentDaoRepository

class InsertPaymentsUseCase @Inject constructor(
    private val paymentDaoRepository: PaymentDaoRepository
) {
    suspend operator fun invoke(payments: List<PaymentModel>) {
        paymentDaoRepository.insertPayments(payments)
    }
}