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
import com.yjotdev.login.domain.model.EmailModel
import com.yjotdev.login.domain.repository.EmailRepository
import com.yjotdev.login.domain.usecase.email.SendEmailUseCase

/**
 * Pruebas unitarias para el caso de uso de envío de Email.
 */
class EmailUseCaseTest {

    private lateinit var emailRepository: EmailRepository
    private lateinit var sendEmailUseCase: SendEmailUseCase

    @Before
    fun setUp() {
        emailRepository = mockk()
        sendEmailUseCase = SendEmailUseCase(emailRepository)
    }

    @Test
    fun whenSendEmailUseCaseIsInvokedSuccessfullyThenItReturnsSuccess() = runTest {
        // Given
        val emailModel = EmailModel(to = "test@example.com", subject = "Test", text = "This is a test")
        coEvery { emailRepository.sendEmail(emailModel) } returns Result.Success(Unit)

        // When
        val result = sendEmailUseCase(emailModel)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { emailRepository.sendEmail(emailModel) }
    }

    @Test
    fun whenSendEmailUseCaseFailsThenItReturnsAnError() = runTest {
        // Given
        val emailModel = EmailModel(to = "test@example.com", subject = "Test", text = "This is a test")
        val exception = Exception("Email service is down")
        coEvery { emailRepository.sendEmail(emailModel) } returns Result.Error(exception)

        // When
        val result = sendEmailUseCase(emailModel)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { emailRepository.sendEmail(emailModel) }
    }
}
