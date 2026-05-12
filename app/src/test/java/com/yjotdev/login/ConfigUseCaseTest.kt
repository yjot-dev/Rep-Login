package com.yjotdev.login

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.yjotdev.login.domain.repository.ConfigRepository
import com.yjotdev.login.domain.usecase.config.GetConfigUseCase
import com.yjotdev.login.domain.usecase.config.SaveTokenFcmUseCase

/**
 * Pruebas unitarias para el caso de uso de Configuración.
 */
class ConfigUseCaseTest {

    private lateinit var configRepository: ConfigRepository
    private lateinit var getConfigUseCase: GetConfigUseCase
    private lateinit var saveTokenFcmUseCase: SaveTokenFcmUseCase

    @Before
    fun setUp() {
        configRepository = mockk()
        getConfigUseCase = GetConfigUseCase(configRepository)
        saveTokenFcmUseCase = SaveTokenFcmUseCase(configRepository)
    }

    @Test
    fun getConfigUseCaseReturnsMapFromRepository() {
        // Given
        val expectedConfig = mutableMapOf<String, String?>("api_key" to "12345", "env" to "prod")
        every { configRepository.getConfig() } returns expectedConfig

        // When
        val result = getConfigUseCase()

        // Then
        assertEquals(expectedConfig, result)
        assertEquals("12345", result["api_key"])
        verify(exactly = 1) { configRepository.getConfig() }
    }

    @Test
    fun saveTokenFcmUseCaseSaveLocalDataCorrectly() {
        // Given
        val token = "fcm_token"
        every { configRepository.saveTokenFCM(token) } returns Unit

        // When
        saveTokenFcmUseCase(token)

        // Then
        verify(exactly = 1) { configRepository.saveTokenFCM(token) }
    }
}