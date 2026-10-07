package com.yjotdev.login.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.data.local.dao.PaymentDao
import com.yjotdev.login.data.local.mapper.toBD
import com.yjotdev.login.data.local.mapper.toDomain
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentDaoRepository

@Singleton
class PaymentDaoRepositoryImpl @Inject constructor(
    private val paymentDao: PaymentDao
): PaymentDaoRepository {
    override fun getPaymentsByUserId(userId: Int, limit: Int): Flow<List<PaymentModel>> {
        return paymentDao.getPaymentsByUserId(userId, limit).map { items ->
            items.map { it.toDomain() }
        }
    }

    override suspend fun insertPayments(payments: List<PaymentModel>) {
        return paymentDao.insertPayments(payments.map { it.toBD() })
    }
}