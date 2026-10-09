package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.mapSuccess
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.ValidateModel
import com.yjotdev.login.domain.repository.PaymentApiRepository
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
class PaymentApiRepositoryImpl @Inject constructor(
    private val paymentApi: PaymentApi
) : PaymentApiRepository {

    override suspend fun getRemotePayments(userId: Int): Result<List<PaymentModel>> {
        return safeApiCallForBody { paymentApi.getRemotePayments(userId) }
            .mapSuccess { result -> result.map { it.toDomain() }}
    }

    override suspend fun validatePayment(validate: ValidateModel): Result<Unit> {
        return safeApiCallForUnit { paymentApi.validatePayment(validate.toDto()) }
    }
}