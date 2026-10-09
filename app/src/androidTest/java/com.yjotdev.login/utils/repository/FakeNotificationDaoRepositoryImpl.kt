package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.repository.NotificationDaoRepository

@Singleton
class FakeNotificationDaoRepositoryImpl @Inject constructor()
    : NotificationDaoRepository {
    private var fakeData = listOf(
        NotificationModel(
            id = 1,
            message = "Notification 1",
            date = "2023-07-01",
            userId = 1
        ),
        NotificationModel(
            id = 2,
            message = "Notification 2",
            date = "2023-08-03",
            userId = 1
        )
    )

    override suspend fun insertLocalNotifications(notifications: List<NotificationModel>) {
        if (!fakeData.containsAll(notifications)) {
            fakeData = notifications
        }
    }

    override fun getLocalNotifications(userId: Int, limit: Int): Flow<List<NotificationModel>> {
        return flow {
            val filtered = fakeData.filter {
                it.userId == userId
            }
            emit(filtered)
        }
    }
}