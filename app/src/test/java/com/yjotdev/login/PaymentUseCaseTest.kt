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
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.repository.PaymentRepository
import com.yjotdev.login.domain.usecase.payment.CaptureOrderUseCase
import com.yjotdev.login.domain.usecase.payment.CreateOrderUseCase
import com.yjotdev.login.domain.usecase.payment.SelectPaymentsUseCase

/**
 * Pruebas unitarias para los casos de uso de Pagos.
 */
class PaymentUseCaseTest {

    private lateinit var paymentRepository: PaymentRepository
    private lateinit var createOrderUseCase: CreateOrderUseCase
    private lateinit var captureOrderUseCase: CaptureOrderUseCase
    private lateinit var selectPaymentsUseCase: SelectPaymentsUseCase

    @Before
    fun setUp() {
        paymentRepository = mockk()
        createOrderUseCase = CreateOrderUseCase(paymentRepository)
        captureOrderUseCase = CaptureOrderUseCase(paymentRepository)
        selectPaymentsUseCase = SelectPaymentsUseCase(paymentRepository)
    }

    @Test
    fun createOrderUseCaseReturnsSuccessWhenRepositoryIsSuccessful() = runTest {
        // Given
        val request = CreateOrderRequestModel(plan = "premium", userId = 1)
        val expectedResponse = CreateOrderResultModel(approveUrl = "https://paypal.com/approve")
        coEvery { paymentRepository.createOrder(request) } returns Result.Success(expectedResponse)

        // When
        val result = createOrderUseCase(request)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedResponse, (result as Result.Success).data)
        coVerify(exactly = 1) { paymentRepository.createOrder(request) }
    }

    @Test
    fun captureOrderUseCaseCallsRepositoryCorrectly() = runTest {
        // Given
        val captureRequest = CaptureOrderRequestModel(orderId = "ORD-123")
        coEvery { paymentRepository.captureOrder(captureRequest) } returns Result.Success(Unit)

        // When
        val result = captureOrderUseCase(captureRequest)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { paymentRepository.captureOrder(captureRequest) }
    }

    @Test
    fun selectPaymentsUseCaseReturnsListOfPayments() = runTest {
        // Given
        val userId = 1
        val fakePayments = listOf(PaymentModel(id = 100, amount = 50.0f, moneyCode = "USD"))
        coEvery { paymentRepository.selectPayments(userId, any()) } returns Result.Success(fakePayments)

        // When
        val result = selectPaymentsUseCase(userId, 10)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakePayments, (result as Result.Success).data)
        coVerify(exactly = 1) { paymentRepository.selectPayments(userId, 10) }
    }
}