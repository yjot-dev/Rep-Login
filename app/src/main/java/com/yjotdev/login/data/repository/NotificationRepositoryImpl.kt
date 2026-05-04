package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.mapSuccess
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.model.SendNotificationRequestModel
import com.yjotdev.login.domain.repository.NotificationRepository
import com.yjotdev.login.data.remote.service.NotificationService
import com.yjotdev.login.data.remote.core.safeApiCallForBody
import com.yjotdev.login.data.remote.core.safeApiCallForUnit
import com.yjotdev.login.data.remote.mapper.toDomain
import com.yjotdev.login.data.remote.mapper.toDto

/**
 * Implementación del NotificationRepository.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationRepository {

    override suspend fun selectNotifications(userId: Int, maxRows: Int?): Result<List<NotificationModel>> {
        return safeApiCallForBody { notificationService.selectNotifications(userId, maxRows) }
            .mapSuccess { result -> result.map { it.toDomain() }}
    }

    override suspend fun sendNotification(body: SendNotificationRequestModel): Result<Unit> {
        return safeApiCallForUnit { notificationService.sendNotification(body.toDto()) }
    }
}