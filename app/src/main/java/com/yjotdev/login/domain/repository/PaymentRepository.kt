package com.yjotdev.login.domain.repository

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
import com.yjotdev.login.domain.model.CaptureOrderResultModel
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.model.PaymentModel

/**
 * Define el contrato para las operaciones del repositorio de pagos.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve tipos de datos del Dominio (PaymentModel).
 */
interface PaymentRepository {
    /**
     * Busca los pagos del usuario
     * @return Result<PaymentModel> que contiene el pago si se encuentra, o un error.
     */
    suspend fun selectPayments(): Result<PaymentModel>

    /**
     * Crea la orden de pago mediante el tipo de plan seleccionado
     * @return Result<Unit> que indica éxito o un error.
     * **/
    suspend fun createOrder(plan: CreateOrderRequestModel): Result<CreateOrderResultModel>

    /**
     * Captura la orden de pago mediante el id de la orden
     * @return Result<Unit> que indica éxito o un error.
     * **/
    suspend fun captureOrder(orderId: CaptureOrderRequestModel): Result<CaptureOrderResultModel>
}