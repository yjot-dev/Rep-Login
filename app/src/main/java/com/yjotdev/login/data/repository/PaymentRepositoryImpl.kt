package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.mapSuccess
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
import com.yjotdev.login.domain.model.CaptureOrderResultModel
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.repository.PaymentRepository
import com.yjotdev.login.data.remote.service.PaymentService
import com.yjotdev.login.data.remote.core.safeApiCallForBody
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
    private val paymentService: PaymentService
) : PaymentRepository {

    override suspend fun selectPayments(): Result<PaymentModel> {
        return safeApiCallForBody { paymentService.selectPayments() }
            .mapSuccess { result -> result.toDomain() }
    }

    override suspend fun createOrder(plan: CreateOrderRequestModel): Result<CreateOrderResultModel> {
        return safeApiCallForBody { paymentService.createOrder(plan.toDto()) }
            .mapSuccess { result -> result.toDomain() }
    }

    override suspend fun captureOrder(orderId: CaptureOrderRequestModel): Result<CaptureOrderResultModel> {
        return safeApiCallForBody { paymentService.captureOrder(orderId.toDto()) }
            .mapSuccess { result -> result.toDomain() }
    }
}