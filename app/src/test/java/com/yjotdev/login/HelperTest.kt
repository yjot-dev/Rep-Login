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
    fun isValidUserGivenValidUsernameWhenValidatedThenReturnsTrue() {
        // Given
        val input = "JohnDoe"

        // When
        val result = Helper.isValidUser(input)

        // Then
        assertTrue("El usuario debería ser válido", result)
    }

    @Test
    fun isValidUserGivenUsernameTooShortWhenValidatedThenReturnsFalse() {
        // Given
        val input = "Jo"

        // When
        val result = Helper.isValidUser(input)

        // Then
        assertFalse("El usuario es demasiado corto", result)
    }

    @Test
    fun isValidUserGivenUsernameWithNumbersWhenValidatedThenReturnsFalse() {
        // Given
        val input = "User123"

        // When
        val result = Helper.isValidUser(input)

        // Then
        assertFalse("El usuario no debería contener números", result)
    }

    // Pruebas para isValidEmail
    @Test
    fun isValidEmailGivenValidEmailWhenValidatedThenReturnsTrue() {
        // Given
        val input = "test@example.com"

        // When
        val result = Helper.isValidEmail(input)

        // Then
        assertTrue("El email debería ser válido", result)
    }

    @Test
    fun isValidEmailGivenEmailWithoutAtSymbolWhenValidatedThenReturnsFalse() {
        // Given
        val input = "invalidemail.com"

        // When
        val result = Helper.isValidEmail(input)

        // Then
        assertFalse("El email no tiene símbolo @", result)
    }

    // Pruebas para isValidUserOrEmail
    @Test
    fun isValidUserOrEmailGivenValidUsernameWhenValidatedThenReturnsTrue() {
        // Given
        val input = "JaneDoe"

        // When
        val result = Helper.isValidUserOrEmail(input)

        // Then
        assertTrue(result)
    }

    @Test
    fun isValidUserOrEmailGivenValidEmailWhenValidatedThenReturnsTrue() {
        // Given
        val input = "jane@example.com"

        // When
        val result = Helper.isValidUserOrEmail(input)

        // Then
        assertTrue(result)
    }

    // Pruebas para isValidPassword
    @Test
    fun isValidPasswordGivenValidPasswordWhenValidatedThenReturnsTrue() {
        // Given
        val input = "Pass1234@"

        // When
        val result = Helper.isValidPassword(input)

        // Then
        assertTrue("La contraseña cumple con los requisitos", result)
    }

    @Test
    fun isValidPasswordGivenPasswordTooShortWhenValidatedThenReturnsFalse() {
        // Given
        val input = "short"

        // When
        val result = Helper.isValidPassword(input)

        // Then
        assertFalse("La contraseña es inferior a 8 caracteres", result)
    }

    // Pruebas para moneyCodeByCountry
    @Test
    fun moneyCodeByCountryGivenEcuadorWhenRequestedThenReturnsUsdForBoth() {
        // Given
        val countryCode = "EC"

        // When
        val result = Helper.moneyCodeByCountry(countryCode)

        // Then
        assertEquals("USD", result) // Moneda local
    }

    @Test
    fun moneyCodeByCountryGivenArgentinaWhenRequestedThenReturnsArsAndUsd() {
        // Given
        val countryCode = "AR"

        // When
        val result = Helper.moneyCodeByCountry(countryCode)

        // Then
        assertEquals("ARS", result)
    }

    @Test
    fun moneyCodeByCountryGivenUnknownCountryWhenRequestedThenReturnsUsdAsDefault() {
        // Given
        val countryCode = "XX"

        // When
        val result = Helper.moneyCodeByCountry(countryCode)

        // Then
        assertEquals("USD", result)
    }

    // Pruebas para moneyConvertString
    @Test
    fun moneyConvertStringGiven10UsdToMxnWhenConvertedThenReturnsCorrectFormattedString() {
        // Given
        val amount = 10.0f
        val moneyCode = "MXN"
        // MXN is 17.23 then 10 * 17.23 = 172.3
        val expected = "172.3 MXN"

        // When
        val result = Helper.moneyConvertString(amount, moneyCode)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun moneyConvertStringGiven1UsdToEurWhenConvertedThenReturnsCorrectFormattedString() {
        // Given
        val amount = 1.0f
        val moneyCode = "EUR"
        val expected = "0.87 EUR"

        // When
        val result = Helper.moneyConvertString(amount, moneyCode)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun moneyConvertStringGivenAnUnknownCurrencyWhenConvertedThenDefaultsToOneToOneRatio() {
        // Given
        val amount = 50.0f
        val moneyCode = "XYZ"
        val expected = "50.0 XYZ"

        // When
        val result = Helper.moneyConvertString(amount, moneyCode)

        // Then
        assertEquals(expected, result)
    }

    // Pruebas para getAmountFromProductId
    @Test
    fun getAmountFromProductIdGivenValidIdWhenRequestedThenReturnsCorrectAmount() {
        // Given
        val productId = "support_lv1"
        val expected = 1.0f

        // When
        val result = Helper.getAmountFromProductId(productId)

        // Then
        assertEquals(expected, result, 0.0f)
    }

    @Test
    fun getAmountFromProductIdGivenInvalidOrNullIdWhenRequestedThenReturnsZero() {
        // Given
        val productId = "invalid_id"
        val expected = 0.0f

        // When
        val result = Helper.getAmountFromProductId(productId)

        // Then
        assertEquals(expected, result, 0.0f)
    }
}