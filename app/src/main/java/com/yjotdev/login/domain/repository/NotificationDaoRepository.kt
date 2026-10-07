package com.yjotdev.login.domain.repository

import kotlinx.coroutines.flow.Flow
import com.yjotdev.login.domain.model.NotificationModel

interface NotificationDaoRepository {
    fun getNotificationsByUserId(userId: Int, limit: Int): Flow<List<NotificationModel>>

    suspend fun insertNotifications(notifications: List<NotificationModel>)
}