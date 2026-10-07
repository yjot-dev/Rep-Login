package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.yjotdev.login.domain.repository.PaymentDaoRepository
import com.yjotdev.login.domain.model.PaymentModel

@Singleton
class FakePaymentDaoRepositoryImpl @Inject constructor()
    : PaymentDaoRepository {
    private var fakeData = listOf(
        PaymentModel(
            id = 1,
            amount = 100.0f,
            money = "USD",
            purchaseToken = "token1",
            date = "2023-07-01",
            userId = 1
        ),
        PaymentModel(
            id = 2,
            amount = 200.0f,
            money = "USD",
            purchaseToken = "token2",
            date = "2023-08-03",
            userId = 1
        )
    )

    override suspend fun insertPayments(payments: List<PaymentModel>) {
        if (!fakeData.containsAll(payments)) {
            fakeData = payments
        }
    }

    override fun getPaymentsByUserId(userId: Int, limit: Int): Flow<List<PaymentModel>> {
        return flow {
            val filtered = fakeData.filter {
                it.userId == userId
            }
            emit(filtered)
        }
    }
}