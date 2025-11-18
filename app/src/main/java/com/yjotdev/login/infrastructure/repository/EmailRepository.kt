package com.yjotdev.login.infrastructure.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.EmailEntity
import com.yjotdev.login.domain.port.EmailPort
import com.yjotdev.login.infrastructure.network.client.Api
import com.yjotdev.login.infrastructure.network.core.safeApiCallForUnit
/**
 * Implementación del EmailPort.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class EmailRepository @Inject constructor(
    private val api: Api
) : EmailPort {
    override suspend fun sendEmail(email: EmailEntity): Result<Unit> {
        return safeApiCallForUnit { api.getEmailRetrofit().sendEmail(email) }
    }
}