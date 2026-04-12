package com.yjotdev.login.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import com.yjotdev.login.data.remote.dto.NotificationDto

/**
 * Interfaz de Retrofit para las operaciones de la API de notificaciones.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface NotificationService {
    @GET("notifications")
    suspend fun selectNotifications(): Response<NotificationDto>
}