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
import com.yjotdev.login.domain.entity.EmailEntity
import com.yjotdev.login.domain.port.EmailPort
import com.yjotdev.login.domain.usecase.email.EmailUseCase

/**
 * Pruebas unitarias para el caso de uso de envío de Email.
 */
class EmailUseCaseTest {

    private lateinit var emailPort: EmailPort
    private lateinit var emailUseCase: EmailUseCase

    @Before
    fun setUp() {
        emailPort = mockk()
        emailUseCase = EmailUseCase(emailPort)
    }

    @Test
    fun whenSendEmailUseCaseIsInvokedSuccessfullyThenItReturnsSuccess() = runTest {
        // Given
        val emailEntity = EmailEntity(to = "test@example.com", subject = "Test", text = "This is a test")
        coEvery { emailPort.sendEmail(emailEntity) } returns Result.Success(Unit)

        // When
        val result = emailUseCase(emailEntity)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { emailPort.sendEmail(emailEntity) }
    }

    @Test
    fun whenSendEmailUseCaseFailsThenItReturnsAnError() = runTest {
        // Given
        val emailEntity = EmailEntity(to = "test@example.com", subject = "Test", text = "This is a test")
        val exception = Exception("Email service is down")
        coEvery { emailPort.sendEmail(emailEntity) } returns Result.Error(exception)

        // When
        val result = emailUseCase(emailEntity)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { emailPort.sendEmail(emailEntity) }
    }
}
