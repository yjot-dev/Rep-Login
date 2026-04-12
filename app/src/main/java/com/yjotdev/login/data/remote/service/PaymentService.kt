package com.yjotdev.login.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import com.yjotdev.login.data.remote.dto.PaymentDto

/**
 * Interfaz de Retrofit para las operaciones de la API de pagos.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface PaymentService {
    @GET("payments")
    suspend fun selectPayments(): Response<PaymentDto>
}