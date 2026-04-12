package com.yjotdev.login

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.yjotdev.login.domain.repository.StringRepository
import com.yjotdev.login.domain.usecase.string.GetStringUseCase

/**
 * Pruebas unitarias para el caso de uso de obtención de Strings.
 */
class StringUseCaseTest {

    private lateinit var stringRepository: StringRepository
    private lateinit var getStringUseCase: GetStringUseCase

    @Before
    fun setUp() {
        stringRepository = mockk()
        getStringUseCase = GetStringUseCase(stringRepository)
    }

    @Test
    fun whenGetStringUseCaseIsInvokedWithoutArgsThenPortMethodIsCalled() {
        // Given
        val resourceId = R.string.app_name
        val expectedString = "Login App"
        every { stringRepository.getString(resourceId) } returns expectedString

        // When
        val result = getStringUseCase(resourceId)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringRepository.getString(resourceId) }
    }

    @Test
    fun whenGetStringUseCaseIsInvokedWithArgsThenPortMethodIsCalled() {
        // Given
        val resourceId = R.string.toast_login_success
        val arg = "TestUser"
        val expectedString = "Welcome, TestUser!"
        every { stringRepository.getString(resourceId, any()) } returns expectedString

        // When
        val result = getStringUseCase(resourceId, arg)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringRepository.getString(resourceId, any()) }
    }
}
