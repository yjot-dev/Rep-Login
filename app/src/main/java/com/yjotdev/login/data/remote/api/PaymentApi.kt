package com.yjotdev.login.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query
import com.yjotdev.login.data.remote.dto.PaymentDto
import com.yjotdev.login.data.remote.dto.CreateOrderRequestDto
import com.yjotdev.login.data.remote.dto.CreateOrderResponseDto
import com.yjotdev.login.data.remote.dto.CaptureOrderRequestDto

/**
 * Interfaz de Retrofit para las operaciones de la API de pagos.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface PaymentApi {
    @GET("payments")
    suspend fun selectPayments(
        @Query("userId") userId: Int,
        @Query("maxRows") maxRows: Int? = null
    ): Response<List<PaymentDto>>

    @POST("payments/create-order")
    suspend fun createOrder(@Body body: CreateOrderRequestDto): Response<CreateOrderResponseDto>

    @POST("payments/capture-order")
    suspend fun captureOrder(@Body orderId: CaptureOrderRequestDto): Response<Unit>
}