package com.yjotdev.login.domain.repository

import kotlinx.coroutines.flow.Flow
import com.yjotdev.login.domain.model.PaymentModel

interface PaymentDaoRepository {
    fun getLocalPayments(userId: Int, limit: Int): Flow<List<PaymentModel>>

    suspend fun insertLocalPayments(payments: List<PaymentModel>)
}