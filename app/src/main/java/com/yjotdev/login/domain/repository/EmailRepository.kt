package com.yjotdev.login.domain.repository

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.EmailModel

/**
 * Define el contrato para las operaciones del repositorio de emails.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve resultados encapsulados.
 */
interface EmailRepository {
    /**
     * Envia un correo electrónico a un usuario
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun sendEmail(email: EmailModel): Result<Unit>
}