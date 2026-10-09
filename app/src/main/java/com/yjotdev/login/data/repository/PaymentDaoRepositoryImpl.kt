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
    override fun getLocalPayments(userId: Int, limit: Int): Flow<List<PaymentModel>> {
        return paymentDao.getLocalPayments(userId, limit).map { items ->
            items.map { it.toDomain() }
        }
    }

    override suspend fun insertLocalPayments(payments: List<PaymentModel>) {
        return paymentDao.insertLocalPayments(payments.map { it.toBD() })
    }
}