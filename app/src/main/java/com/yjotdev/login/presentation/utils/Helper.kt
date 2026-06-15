package com.yjotdev.login.presentation.utils

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

    fun moneyCodeByCountry(countryCode: String): Pair<String,String> {
        // Retorna un par de valores: código de moneda del pais y el código de moneda válido para paypal
        return when(countryCode) {
            "EC" -> Pair("USD","USD")
            "MX" -> Pair("MXN","MXN")
            "ES" -> Pair("EUR","EUR")
            "US" -> Pair("USD","USD")
            "AR" -> Pair("ARS","USD")
            "CA" -> Pair("CAD","CAD")
            "CO" -> Pair("COP","USD")
            "SV" -> Pair("USD","USD")
            "PE" -> Pair("PEN","USD")
            "GB" -> Pair("GBP","GBP")
            else -> Pair("USD","USD")
        }
    }

    fun moneyConvertString(amount: Double, moneyCode: String): String {
        // Simula una conversión de divisas con tasas de cambio fijas
        val exchangeRates = mapOf(
            "USD" to 1.0,
            "MXN" to 17.23,
            "EUR" to 0.87,
            "ARS" to 1428.50,
            "CAD" to 1.40,
            "COP" to 3490.94,
            "PEN" to 3.40,
            "GBP" to 0.75
        )
        val fromRate = exchangeRates["USD"] ?: 1.0
        val toRate = exchangeRates[moneyCode] ?: 1.0
        return "${amount * (toRate / fromRate)} $moneyCode"
    }
}