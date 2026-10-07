package com.yjotdev.login.domain.repository

import kotlinx.coroutines.flow.Flow
import com.yjotdev.login.domain.model.PaymentModel

interface PaymentDaoRepository {
    fun getPaymentsByUserId(userId: Int, limit: Int): Flow<List<PaymentModel>>

    suspend fun insertPayments(payments: List<PaymentModel>)
}