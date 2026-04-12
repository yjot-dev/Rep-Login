package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentRepository

@Singleton
class FakePaymentRepositoryImpl @Inject constructor() : PaymentRepository {

    override suspend fun selectPayments(): Result<PaymentModel> {
        return Result.Success(PaymentModel())
    }
}