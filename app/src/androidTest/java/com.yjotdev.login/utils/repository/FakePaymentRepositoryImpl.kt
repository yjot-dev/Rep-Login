package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CaptureOrderResultModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentRepository

@Singleton
class FakePaymentRepositoryImpl @Inject constructor() : PaymentRepository {

    override suspend fun selectPayments(): Result<PaymentModel> {
        return Result.Success(PaymentModel())
    }

    override suspend fun createOrder(plan: CreateOrderRequestModel): Result<CreateOrderResultModel> {
        return Result.Success(CreateOrderResultModel())
    }

    override suspend fun captureOrder(orderId: CaptureOrderRequestModel): Result<CaptureOrderResultModel> {
        return Result.Success(CaptureOrderResultModel())
    }
}