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

    fun moneyFormatByCountry(countryCode: String): Pair<String,String> {
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
}