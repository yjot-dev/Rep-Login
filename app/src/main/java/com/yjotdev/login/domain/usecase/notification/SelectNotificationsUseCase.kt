package com.yjotdev.login.domain.usecase.notification

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.repository.NotificationRepository

class SelectNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    /** Obtener notificaciones mediante caso de uso **/
    suspend operator fun invoke(): Result<NotificationModel> {
        return notificationRepository.selectNotifications()
    }
}