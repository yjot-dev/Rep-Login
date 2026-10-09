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
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.ValidateModel
import com.yjotdev.login.domain.repository.PaymentApiRepository
import com.yjotdev.login.domain.usecase.payment.GetRemotePaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.ValidatePaymentUseCase

/**
 * Pruebas unitarias para los casos de uso de Pagos.
 */
class PaymentUseCaseTest {

    private lateinit var paymentApiRepository: PaymentApiRepository
    private lateinit var getRemotePaymentsUseCase: GetRemotePaymentsUseCase
    private lateinit var validatePaymentUseCase: ValidatePaymentUseCase

    @Before
    fun setUp() {
        paymentApiRepository = mockk()
        getRemotePaymentsUseCase = GetRemotePaymentsUseCase(paymentApiRepository)
        validatePaymentUseCase = ValidatePaymentUseCase(paymentApiRepository)
    }

    @Test
    fun whenSelectPaymentsUseCaseIsInvokedSuccessfullyThenItReturnsPaymentsList() = runTest {
        // Given
        val userId = 1
        val fakePayments = listOf(PaymentModel(id = 100, amount = 50.0f, money = "USD"))
        coEvery { paymentApiRepository.getRemotePayments(userId) } returns Result.Success(fakePayments)

        // When
        val result = getRemotePaymentsUseCase(userId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakePayments, (result as Result.Success).data)
        coVerify(exactly = 1) { paymentApiRepository.getRemotePayments(userId) }
    }

    @Test
    fun whenSelectPaymentsUseCaseFailsThenItReturnsAnError() = runTest {
        // Given
        val userId = 1
        val exception = Exception("Error al obtener historial de pagos")
        coEvery { paymentApiRepository.getRemotePayments(userId) } returns Result.Error(exception)

        // When
        val result = getRemotePaymentsUseCase(userId)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { paymentApiRepository.getRemotePayments(userId) }
    }

    @Test
    fun whenValidatePaymentUseCaseIsInvokedSuccessfullyThenItReturnsSuccess() = runTest {
        // Given
        val validateModel = ValidateModel(
            purchaseToken = "token123",
            productId = "product1",
            userId = 1,
            amount = 10.0f,
            money = "USD",
            date = "2024-05-11 10:00:00"
        )
        coEvery { paymentApiRepository.validatePayment(validateModel) } returns Result.Success(Unit)

        // When
        val result = validatePaymentUseCase(validateModel)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify(exactly = 1) { paymentApiRepository.validatePayment(validateModel) }
    }

    @Test
    fun whenValidatePaymentUseCaseFailsThenItReturnsAnError() = runTest {
        // Given
        val validateModel = ValidateModel(
            purchaseToken = "token123",
            productId = "product1",
            userId = 1,
            amount = 10.0f,
            money = "USD",
            date = "2024-05-11 10:00:00"
        )
        val exception = Exception("Error al validar pago")
        coEvery { paymentApiRepository.validatePayment(validateModel) } returns Result.Error(exception)

        // When
        val result = validatePaymentUseCase(validateModel)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { paymentApiRepository.validatePayment(validateModel) }
    }
}