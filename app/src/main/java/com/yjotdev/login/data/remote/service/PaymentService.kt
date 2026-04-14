package com.yjotdev.login.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import com.yjotdev.login.data.remote.dto.PaymentDto
import com.yjotdev.login.data.remote.dto.CreateOrderRequestDto
import com.yjotdev.login.data.remote.dto.CaptureOrderRequestDto
import com.yjotdev.login.data.remote.dto.CaptureOrderResponseDto
import com.yjotdev.login.data.remote.dto.CreateOrderResponseDto

/**
 * Interfaz de Retrofit para las operaciones de la API de pagos.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface PaymentService {
    @GET("payments")
    suspend fun selectPayments(): Response<PaymentDto>

    @POST("payments/create-order")
    suspend fun createOrder(@Body plan: CreateOrderRequestDto): Response<CreateOrderResponseDto>

    @POST("payments/capture-order")
    suspend fun captureOrder(@Body orderId: CaptureOrderRequestDto): Response<CaptureOrderResponseDto>
}