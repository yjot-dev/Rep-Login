package com.yjotdev.login.domain.repository

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.model.CaptureOrderRequestModel

/**
 * Define el contrato para las operaciones del repositorio de pagos.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve tipos de datos del Dominio (PaymentModel).
 */
interface PaymentRepository {
    /**
     * Selecciona todos los pagos del usuario en la BD
     * @return Result<List<PaymentModel>> que contiene los pagos del usuario si
     * se encuentra, o un error.
     */
    suspend fun selectPayments(userId: Int, maxRows: Int? = null): Result<List<PaymentModel>>

    /**
     * Crea la orden de pago mediante el tipo de plan seleccionado
     * @return Result<CreateOrderResultModel> que contiene la creación de la
     * orden del usuario si se encuentra, o un error.
     * **/
    suspend fun createOrder(body: CreateOrderRequestModel): Result<CreateOrderResultModel>

    /**
     * Captura la orden de pago mediante ID de la orden
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun captureOrder(orderId: CaptureOrderRequestModel): Result<Unit>
}