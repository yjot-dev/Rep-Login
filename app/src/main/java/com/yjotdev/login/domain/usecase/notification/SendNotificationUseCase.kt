package com.yjotdev.login.domain.usecase.notification

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.SendNotificationRequestModel
import com.yjotdev.login.domain.repository.NotificationApiRepository

class SendNotificationUseCase @Inject constructor(
    private val notificationApiRepository: NotificationApiRepository
) {
    /** Enviar notificacion mediante caso de uso **/
    suspend operator fun invoke(body: SendNotificationRequestModel): Result<Unit> {
        return notificationApiRepository.sendNotification(body)
    }
}