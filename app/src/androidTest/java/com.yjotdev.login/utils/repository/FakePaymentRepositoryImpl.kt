package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentRepository

@Singleton
class FakePaymentRepositoryImpl @Inject constructor() : PaymentRepository {

    override suspend fun selectPayments(userId: Int, maxRows: Int?): Result<List<PaymentModel>> {
        return Result.Success(listOf(PaymentModel()))
    }

    override suspend fun createOrder(body: CreateOrderRequestModel): Result<Map<String,String>> {
        return Result.Success(mapOf())
    }

    override suspend fun captureOrder(orderId: Map<String,String>): Result<Unit> {
        return Result.Success(Unit)
    }
}