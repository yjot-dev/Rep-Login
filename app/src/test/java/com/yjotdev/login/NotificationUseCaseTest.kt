package com.yjotdev.login

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.model.SendNotificationRequestModel
import com.yjotdev.login.domain.repository.NotificationApiRepository
import com.yjotdev.login.domain.usecase.notification.FindNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.SendNotificationUseCase

/**
 * Pruebas unitarias para los casos de uso de Notificaciones.
 */
class NotificationUseCaseTest {

    private lateinit var notificationApiRepository: NotificationApiRepository
    private lateinit var findNotificationsUseCase: FindNotificationsUseCase
    private lateinit var sendNotificationUseCase: SendNotificationUseCase

    @Before
    fun setUp() {
        notificationApiRepository = mockk()
        findNotificationsUseCase = FindNotificationsUseCase(notificationApiRepository)
        sendNotificationUseCase = SendNotificationUseCase(notificationApiRepository)
    }

    @Test
    fun selectNotificationsUseCaseReturnsSuccessWithData() = runTest {
        // Given
        val userId = 1
        val fakeNotifications = listOf(NotificationModel(id = 1, message = "Pago realizado", date = "11-05-2026"))
        coEvery { notificationApiRepository.selectNotifications(userId) } returns Result.Success(fakeNotifications)

        // When
        val result = findNotificationsUseCase(userId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakeNotifications, (result as Result.Success).data)
        coVerify(exactly = 1) { notificationApiRepository.selectNotifications(userId) }
    }

    @Test
    fun selectNotificationsUseCaseReturnsErrorWhenSelectionFails() = runTest {
        // Given
        val userId = 1
        val exception = Exception("Error al obtener notificaciones")
        coEvery { notificationApiRepository.selectNotifications(userId) } returns Result.Error(exception)

        // When
        val result = findNotificationsUseCase(userId)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { notificationApiRepository.selectNotifications(userId) }
    }

    @Test
    fun sendNotificationUseCaseReturnsSuccessWhenNotificationIsSent() = runTest {
        // Given
        val request = SendNotificationRequestModel(token = "fcm_token", title = "Aviso FCM", body = "Notificacion FCM enviada")
        coEvery { notificationApiRepository.sendNotification(request) } returns Result.Success(Unit)

        // When
        val result = sendNotificationUseCase(request)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { notificationApiRepository.sendNotification(request) }
    }

    @Test
    fun sendNotificationUseCaseReturnsErrorWhenSendingFails() = runTest {
        // Given
        val request = SendNotificationRequestModel(token = "fcm_token", title = "Aviso FCM", body = "Notificacion FCM enviada")
        val exception = Exception("Error al enviar notificación")
        coEvery { notificationApiRepository.sendNotification(request) } returns Result.Error(exception)

        // When
        val result = sendNotificationUseCase(request)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { notificationApiRepository.sendNotification(request) }
    }
}