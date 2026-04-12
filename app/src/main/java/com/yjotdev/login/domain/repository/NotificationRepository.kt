package com.yjotdev.login.domain.repository

import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.NotificationModel

/**
 * Define el contrato para las operaciones del repositorio de notificaciones.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve tipos de datos del Dominio (NotificationModel).
 */
interface NotificationRepository {
    /**
     * Busca las notificaciones del usuario
     * @return Result<NotificationModel> que contiene el usuario si se encuentra, o un error.
     */
    suspend fun selectNotifications(): Result<NotificationModel>
}