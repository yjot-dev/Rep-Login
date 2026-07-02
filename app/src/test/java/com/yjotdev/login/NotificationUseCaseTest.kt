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
import com.yjotdev.login.domain.repository.NotificationRepository
import com.yjotdev.login.domain.usecase.notification.SelectNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.SendNotificationUseCase

/**
 * Pruebas unitarias para los casos de uso de Notificaciones.
 */
class NotificationUseCaseTest {

    private lateinit var notificationRepository: NotificationRepository
    private lateinit var selectNotificationsUseCase: SelectNotificationsUseCase
    private lateinit var sendNotificationUseCase: SendNotificationUseCase

    @Before
    fun setUp() {
        notificationRepository = mockk()
        selectNotificationsUseCase = SelectNotificationsUseCase(notificationRepository)
        sendNotificationUseCase = SendNotificationUseCase(notificationRepository)
    }

    @Test
    fun selectNotificationsUseCaseReturnsSuccessWithData() = runTest {
        // Given
        val userId = 1
        val fakeNotifications = listOf(NotificationModel(id = 1, message = "Pago realizado", date = "11-05-2026"))
        coEvery { notificationRepository.selectNotifications(userId, any()) } returns Result.Success(fakeNotifications)

        // When
        val result = selectNotificationsUseCase(userId, 5)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakeNotifications, (result as Result.Success).data)
        coVerify(exactly = 1) { notificationRepository.selectNotifications(userId, 5) }
    }

    @Test
    fun selectNotificationsUseCaseReturnsErrorWhenSelectionFails() = runTest {
        // Given
        val userId = 1
        val exception = Exception("Error al obtener notificaciones")
        coEvery { notificationRepository.selectNotifications(userId, any()) } returns Result.Error(exception)

        // When
        val result = selectNotificationsUseCase(userId, 5)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { notificationRepository.selectNotifications(userId, 5) }
    }

    @Test
    fun sendNotificationUseCaseReturnsSuccessWhenNotificationIsSent() = runTest {
        // Given
        val request = SendNotificationRequestModel(token = "fcm_token", title = "Aviso FCM", body = "Notificacion FCM enviada")
        coEvery { notificationRepository.sendNotification(request) } returns Result.Success(Unit)

        // When
        val result = sendNotificationUseCase(request)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { notificationRepository.sendNotification(request) }
    }

    @Test
    fun sendNotificationUseCaseReturnsErrorWhenSendingFails() = runTest {
        // Given
        val request = SendNotificationRequestModel(token = "fcm_token", title = "Aviso FCM", body = "Notificacion FCM enviada")
        val exception = Exception("Error al enviar notificación")
        coEvery { notificationRepository.sendNotification(request) } returns Result.Error(exception)

        // When
        val result = sendNotificationUseCase(request)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { notificationRepository.sendNotification(request) }
    }
}