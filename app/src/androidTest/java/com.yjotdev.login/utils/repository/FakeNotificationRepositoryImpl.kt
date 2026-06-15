package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.model.SendNotificationRequestModel
import com.yjotdev.login.domain.repository.NotificationRepository

@Singleton
class FakeNotificationRepositoryImpl @Inject constructor() : NotificationRepository {

    override suspend fun selectNotifications(userId: Int, maxRows: Int?): Result<List<NotificationModel>> {
        return if (userId > 0) {
            Result.Success(listOf(NotificationModel()))
        } else {
            Result.Error(Exception("Failed to fetch notifications"))
        }
    }

    override suspend fun sendNotification(body: SendNotificationRequestModel): Result<Unit> {
        return if (body.userId > 0) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Invalid notification body"))
        }
    }
}