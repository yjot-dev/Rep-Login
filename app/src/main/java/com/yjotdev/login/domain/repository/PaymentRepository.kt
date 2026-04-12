package com.yjotdev.login.domain.repository

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel

/**
 * Define el contrato para las operaciones del repositorio de pagos.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve tipos de datos del Dominio (PaymentModel).
 */
interface PaymentRepository {
    /**
     * Busca los pagos del usuario
     * @return Result<PaymentModel> que contiene el usuario si se encuentra, o un error.
     */
    suspend fun selectPayments(): Result<PaymentModel>
}