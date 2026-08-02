package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.ValidateModel
import com.yjotdev.login.domain.repository.PaymentRepository

@Singleton
class FakePaymentRepositoryImpl @Inject constructor() : PaymentRepository {

    override suspend fun selectPayments(userId: Int, maxRows: Int?): Result<List<PaymentModel>> {
        return if (userId > 0) {
            Result.Success(listOf(PaymentModel()))
        } else {
            Result.Error(Exception("Invalid user ID"))
        }
    }

    override suspend fun validatePayment(validate: ValidateModel): Result<Unit> {
        return if (validate.purchaseToken.isNotEmpty()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Invalid purchase token"))
        }
    }
}