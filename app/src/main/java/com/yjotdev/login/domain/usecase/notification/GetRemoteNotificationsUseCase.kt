package com.yjotdev.login.domain.usecase.notification

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.repository.NotificationApiRepository

class GetRemoteNotificationsUseCase @Inject constructor(
    private val notificationApiRepository: NotificationApiRepository
) {
    /** Obtener notificaciones mediante caso de uso **/
    suspend operator fun invoke(userId: Int): Result<List<NotificationModel>> {
        return notificationApiRepository.getRemoteNotifications(userId)
    }
}