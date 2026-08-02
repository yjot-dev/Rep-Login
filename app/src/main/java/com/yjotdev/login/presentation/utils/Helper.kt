package com.yjotdev.login.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Helper {
    fun isValidUser(input: String): Boolean{
        return Regex("^[A-Za-z]{3,10}$").matches(input)
    }

    fun isValidEmail(input: String): Boolean{
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(input)
    }

    fun isValidUserOrEmail(input: String): Boolean{
        return Regex("^([A-Za-z]{3,10}|[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+)$").matches(input)
    }

    fun isValidPassword(input: String): Boolean{
        return Regex("^[A-Za-z0-9@#_]{8,16}$").matches(input)
    }

    fun moneyCodeByCountry(countryCode: String): String {
        // Retorna el código de moneda del país
        return when(countryCode) {
            "EC" -> "USD"
            "MX" -> "MXN"
            "ES" -> "EUR"
            "US" -> "USD"
            "AR" -> "ARS"
            "CA" -> "CAD"
            "CO" -> "COP"
            "SV" -> "USD"
            "PE" -> "PEN"
            "GB" -> "GBP"
            else -> "USD"
        }
    }

    fun moneyConvertString(amount: Float, moneyCode: String): String {
        // Simula una conversión de divisas con tasas de cambio fijas
        val exchangeRates = mapOf(
            "USD" to 1.0f,
            "MXN" to 17.23f,
            "EUR" to 0.87f,
            "ARS" to 1428.50f,
            "CAD" to 1.40f,
            "COP" to 3490.94f,
            "PEN" to 3.40f,
            "GBP" to 0.75f
        )
        val fromRate = exchangeRates["USD"] ?: 1.0f
        val toRate = exchangeRates[moneyCode] ?: 1.0f
        return "${amount * (toRate / fromRate)} $moneyCode"
    }

    fun getAmountFromProductId(productId: String?) = when (productId) {
        "support_lv1" -> 1.0f
        "support_lv2" -> 5.0f
        "support_lv3" -> 10.0f
        else -> 0.0f
    }

    fun getLocalCountry(): String = Locale.getDefault().country

    fun getLocalDate(): String =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
            .format(Date())
}