package com.yjotdev.login

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import com.yjotdev.login.presentation.utils.Helper

/**
 * Pruebas unitarias para la clase [Helper] siguiendo el patrón Given-When-Then.
 */
class HelperTest {

    // Pruebas para isValidUser
    @Test
    fun `isValidUser - Given a valid username - When validated - Then returns true`() {
        // Given
        val input = "JohnDoe"

        // When
        val result = Helper.isValidUser(input)

        // Then
        assertTrue("El usuario debería ser válido", result)
    }

    @Test
    fun `isValidUser - Given a username too short - When validated - Then returns false`() {
        // Given
        val input = "Jo"

        // When
        val result = Helper.isValidUser(input)

        // Then
        assertFalse("El usuario es demasiado corto", result)
    }

    @Test
    fun `isValidUser - Given a username with numbers - When validated - Then returns false`() {
        // Given
        val input = "User123"

        // When
        val result = Helper.isValidUser(input)

        // Then
        assertFalse("El usuario no debería contener números", result)
    }

    // Pruebas para isValidEmail
    @Test
    fun `isValidEmail - Given a valid email - When validated - Then returns true`() {
        // Given
        val input = "test@example.com"

        // When
        val result = Helper.isValidEmail(input)

        // Then
        assertTrue("El email debería ser válido", result)
    }

    @Test
    fun `isValidEmail - Given an email without at symbol - When validated - Then returns false`() {
        // Given
        val input = "invalidemail.com"

        // When
        val result = Helper.isValidEmail(input)

        // Then
        assertFalse("El email no tiene símbolo @", result)
    }

    // Pruebas para isValidUserOrEmail
    @Test
    fun `isValidUserOrEmail - Given a valid username - When validated - Then returns true`() {
        // Given
        val input = "JaneDoe"

        // When
        val result = Helper.isValidUserOrEmail(input)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isValidUserOrEmail - Given a valid email - When validated - Then returns true`() {
        // Given
        val input = "jane@example.com"

        // When
        val result = Helper.isValidUserOrEmail(input)

        // Then
        assertTrue(result)
    }

    // Pruebas para isValidPassword
    @Test
    fun `isValidPassword - Given a valid password - When validated - Then returns true`() {
        // Given
        val input = "Pass1234@"

        // When
        val result = Helper.isValidPassword(input)

        // Then
        assertTrue("La contraseña cumple con los requisitos", result)
    }

    @Test
    fun `isValidPassword - Given a password too short - When validated - Then returns false`() {
        // Given
        val input = "short"

        // When
        val result = Helper.isValidPassword(input)

        // Then
        assertFalse("La contraseña es inferior a 8 caracteres", result)
    }

    // Pruebas para moneyCodeByCountry
    @Test
    fun `moneyCodeByCountry - Given Ecuador (EC) - When requested - Then returns USD for both`() {
        // Given
        val countryCode = "EC"

        // When
        val result = Helper.moneyCodeByCountry(countryCode)

        // Then
        assertEquals("USD", result.first) // Moneda local
        assertEquals("USD", result.second) // Moneda PayPal
    }

    @Test
    fun `moneyCodeByCountry - Given Argentina (AR) - When requested - Then returns ARS and USD`() {
        // Given
        val countryCode = "AR"

        // When
        val result = Helper.moneyCodeByCountry(countryCode)

        // Then
        assertEquals("ARS", result.first)
        assertEquals("USD", result.second)
    }

    @Test
    fun `moneyCodeByCountry - Given unknown country - When requested - Then returns USD as default`() {
        // Given
        val countryCode = "XX"

        // When
        val result = Helper.moneyCodeByCountry(countryCode)

        // Then
        assertEquals("USD", result.first)
        assertEquals("USD", result.second)
    }

    // Pruebas para moneyConvertString
    @Test
    fun `moneyConvertString - Given 10 USD to MXN - When converted - Then returns correct formatted string`() {
        // Given
        val amount = 10.0
        val moneyCode = "MXN"
        // MXN is 17.23 then 10 * 17.23 = 172.3
        val expected = "172.3 MXN"

        // When
        val result = Helper.moneyConvertString(amount, moneyCode)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `moneyConvertString - Given 1 USD to EUR - When converted - Then returns correct formatted string`() {
        // Given
        val amount = 1.0
        val moneyCode = "EUR"
        val expected = "0.87 EUR"

        // When
        val result = Helper.moneyConvertString(amount, moneyCode)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `moneyConvertString - Given an unknown currency - When converted - Then defaults to 1 to 1 ratio`() {
        // Given
        val amount = 50.0
        val moneyCode = "XYZ"
        val expected = "50.0 XYZ"

        // When
        val result = Helper.moneyConvertString(amount, moneyCode)

        // Then
        assertEquals(expected, result)
    }
}