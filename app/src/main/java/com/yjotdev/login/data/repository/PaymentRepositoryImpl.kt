package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.mapSuccess
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
import com.yjotdev.login.domain.repository.PaymentRepository
import com.yjotdev.login.data.remote.api.PaymentApi
import com.yjotdev.login.data.remote.core.safeApiCallForBody
import com.yjotdev.login.data.remote.core.safeApiCallForUnit
import com.yjotdev.login.data.remote.mapper.toDomain
import com.yjotdev.login.data.remote.mapper.toDto

/**
 * Implementación del PaymentRepository.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class PaymentRepositoryImpl @Inject constructor(
    private val paymentApi: PaymentApi
) : PaymentRepository {

    override suspend fun selectPayments(userId: Int, maxRows: Int?): Result<List<PaymentModel>> {
        return safeApiCallForBody { paymentApi.selectPayments(userId, maxRows) }
            .mapSuccess { result -> result.map { it.toDomain() }}
    }

    override suspend fun createOrder(body: CreateOrderRequestModel): Result<CreateOrderResultModel> {
        return safeApiCallForBody { paymentApi.createOrder(body.toDto()) }
            .mapSuccess { result -> result.toDomain() }
    }

    override suspend fun captureOrder(orderId: CaptureOrderRequestModel): Result<Unit> {
        return safeApiCallForUnit { paymentApi.captureOrder(orderId.toDto()) }
    }
}