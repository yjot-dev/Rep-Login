package com.yjotdev.login

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.yjotdev.login.domain.port.StringPort
import com.yjotdev.login.domain.usecase.string.StringUseCase

/**
 * Pruebas unitarias para el caso de uso de obtención de Strings.
 */
class StringUseCaseTest {

    private lateinit var stringPort: StringPort
    private lateinit var stringUseCase: StringUseCase

    @Before
    fun setUp() {
        stringPort = mockk()
        stringUseCase = StringUseCase(stringPort)
    }

    @Test
    fun whenGetStringUseCaseIsInvokedWithoutArgsThenPortMethodIsCalled() {
        // Given
        val resourceId = R.string.app_name
        val expectedString = "Login App"
        every { stringPort.getString(resourceId) } returns expectedString

        // When
        val result = stringUseCase(resourceId)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringPort.getString(resourceId) }
    }

    @Test
    fun whenGetStringUseCaseIsInvokedWithArgsThenPortMethodIsCalled() {
        // Given
        val resourceId = R.string.toast_login_success
        val arg = "TestUser"
        val expectedString = "Welcome, TestUser!"
        every { stringPort.getString(resourceId, any()) } returns expectedString

        // When
        val result = stringUseCase(resourceId, arg)

        // Then
        assertEquals(expectedString, result)
        verify(exactly = 1) { stringPort.getString(resourceId, any()) }
    }
}
