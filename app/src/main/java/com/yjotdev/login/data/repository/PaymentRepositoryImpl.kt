package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.mapSuccess
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentRepository
import com.yjotdev.login.data.remote.service.PaymentService
import com.yjotdev.login.data.remote.core.safeApiCallForBody
import com.yjotdev.login.data.remote.mapper.toDomain

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
}