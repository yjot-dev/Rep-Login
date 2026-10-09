package com.yjotdev.login.domain.usecase.notification

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.repository.NotificationDaoRepository

class GetLocalNotificationsUseCase @Inject constructor(
    private val notificationDaoRepository: NotificationDaoRepository
) {
    operator fun invoke(userId: Int, limit: Int): Flow<List<NotificationModel>> {
        return notificationDaoRepository.getLocalNotifications(userId, limit)
    }
}