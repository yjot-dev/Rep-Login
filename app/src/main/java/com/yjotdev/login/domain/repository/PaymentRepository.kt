package com.yjotdev.login.domain.repository

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.ValidateModel

/**
 * Define el contrato para las operaciones del repositorio de pagos.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve tipos de datos del Dominio (PaymentModel).
 */
interface PaymentRepository {
    /**
     * selecciona todos los pagos del usuario en la BD
     * @return Result<List<PaymentModel>> que contiene los pagos del usuario si
     * se encuentra, o un error.
     */
    suspend fun selectPayments(userId: Int, maxRows: Int? = null): Result<List<PaymentModel>>

    /**
     * Válida la compra del usuario en el backend con la Google Play Developer API
     * @return Result<Unit> que indica éxito o un error.
     * **/
    suspend fun validatePayment(validate: ValidateModel): Result<Unit>
}