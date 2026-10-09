package com.yjotdev.login.domain.usecase.notification

import javax.inject.Inject
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.repository.NotificationDaoRepository

class InsertLocalNotificationsUseCase @Inject constructor(
    private val notificationDaoRepository: NotificationDaoRepository
) {
    suspend operator fun invoke(notifications: List<NotificationModel>) {
        notificationDaoRepository.insertLocalNotifications(notifications)
    }
}