package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
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

    override suspend fun createOrder(body: CreateOrderRequestModel): Result<CreateOrderResultModel> {
        return if (body.userId > 0) {
            Result.Success(CreateOrderResultModel())
        } else {
            Result.Error(Exception("Invalid body"))
        }
    }

    override suspend fun captureOrder(orderId: CaptureOrderRequestModel): Result<Unit> {
        return if (orderId.orderId != "") {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Invalid order ID"))
        }
    }
}