package com.yjotdev.login.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query
import com.yjotdev.login.data.remote.dto.NotificationDto
import com.yjotdev.login.data.remote.dto.SendNotificationRequestDto

/**
 * Interfaz de Retrofit para las operaciones de la API de notificaciones.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface NotificationService {
    @GET("notifications")
    suspend fun selectNotifications(
        @Query("userId") userId: Int,
        @Query("maxRows") maxRows: Int? = null
    ): Response<List<NotificationDto>>

    @POST("notifications")
    suspend fun sendNotification(@Body body: SendNotificationRequestDto): Response<Unit>
}