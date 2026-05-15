package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.EmailModel
import com.yjotdev.login.domain.repository.EmailRepository
import com.yjotdev.login.data.remote.core.safeApiCallForUnit
import com.yjotdev.login.data.remote.mapper.toDto
import com.yjotdev.login.data.remote.api.EmailApi

/**
 * Implementación del EmailRepository.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class EmailRepositoryImpl @Inject constructor(
    private val emailApi: EmailApi
) : EmailRepository {

    override suspend fun sendEmail(email: EmailModel): Result<Unit> {
        return safeApiCallForUnit { emailApi.sendEmail(email.toDto()) }
    }
}