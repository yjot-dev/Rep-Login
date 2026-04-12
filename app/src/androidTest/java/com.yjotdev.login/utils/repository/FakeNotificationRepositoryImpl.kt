package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.repository.NotificationRepository

@Singleton
class FakeNotificationRepositoryImpl @Inject constructor() : NotificationRepository {

    override suspend fun selectNotifications(): Result<NotificationModel> {
        return Result.Success(NotificationModel())
    }
}