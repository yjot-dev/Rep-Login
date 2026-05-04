package com.yjotdev.login.domain.usecase.notification

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.SendNotificationRequestModel
import com.yjotdev.login.domain.repository.NotificationRepository

class SendNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    /** Enviar notificacion mediante caso de uso **/
    suspend operator fun invoke(body: SendNotificationRequestModel): Result<Unit> {
        return notificationRepository.sendNotification(body)
    }
}