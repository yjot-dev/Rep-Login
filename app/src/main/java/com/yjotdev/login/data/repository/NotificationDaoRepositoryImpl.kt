package com.yjotdev.login.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.data.local.dao.NotificationDao
import com.yjotdev.login.data.local.mapper.toBD
import com.yjotdev.login.data.local.mapper.toDomain
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.repository.NotificationDaoRepository

@Singleton
class NotificationDaoRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
): NotificationDaoRepository {
    override fun getNotificationsByUserId(userId: Int, limit: Int): Flow<List<NotificationModel>> {
        return notificationDao.getNotificationsByUserId(userId, limit).map { items ->
            items.map { it.toDomain() }
        }
    }

    override suspend fun insertNotifications(notifications: List<NotificationModel>) {
        return notificationDao.insertNotifications(notifications.map { it.toBD() })
    }
}