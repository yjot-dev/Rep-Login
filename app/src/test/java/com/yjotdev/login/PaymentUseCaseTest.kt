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
import com.yjotdev.login.domain.repository.PaymentRepository
import com.yjotdev.login.domain.usecase.payment.SelectPaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.ValidatePaymentUseCase

/**
 * Pruebas unitarias para los casos de uso de Pagos.
 */
class PaymentUseCaseTest {

    private lateinit var paymentRepository: PaymentRepository
    private lateinit var selectPaymentsUseCase: SelectPaymentsUseCase
    private lateinit var validatePaymentUseCase: ValidatePaymentUseCase

    @Before
    fun setUp() {
        paymentRepository = mockk()
        selectPaymentsUseCase = SelectPaymentsUseCase(paymentRepository)
        validatePaymentUseCase = ValidatePaymentUseCase(paymentRepository)
    }

    @Test
    fun whenSelectPaymentsUseCaseIsInvokedSuccessfullyThenItReturnsPaymentsList() = runTest {
        // Given
        val userId = 1
        val fakePayments = listOf(PaymentModel(id = 100, amount = 50.0f, money = "USD"))
        coEvery { paymentRepository.selectPayments(userId, any()) } returns Result.Success(fakePayments)

        // When
        val result = selectPaymentsUseCase(userId, 10)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakePayments, (result as Result.Success).data)
        coVerify(exactly = 1) { paymentRepository.selectPayments(userId, 10) }
    }

    @Test
    fun whenSelectPaymentsUseCaseFailsThenItReturnsAnError() = runTest {
        // Given
        val userId = 1
        val exception = Exception("Error al obtener historial de pagos")
        coEvery { paymentRepository.selectPayments(userId, any()) } returns Result.Error(exception)

        // When
        val result = selectPaymentsUseCase(userId, 10)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { paymentRepository.selectPayments(userId, 10) }
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
        coEvery { paymentRepository.validatePayment(validateModel) } returns Result.Success(Unit)

        // When
        val result = validatePaymentUseCase(validateModel)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        coVerify(exactly = 1) { paymentRepository.validatePayment(validateModel) }
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
        coEvery { paymentRepository.validatePayment(validateModel) } returns Result.Error(exception)

        // When
        val result = validatePaymentUseCase(validateModel)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { paymentRepository.validatePayment(validateModel) }
    }
}