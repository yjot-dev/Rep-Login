package com.yjotdev.login.presentation.utils

object Helper {
    fun isValidNumber(input: String): Boolean{
        return Regex("^[0-9]+\$").matches(input)
    }

    fun isValidUser(input: String): Boolean{
        return Regex("^[A-Za-z]{3,10}\$").matches(input)
    }

    fun isValidEmail(input: String): Boolean{
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$").matches(input)
    }

    fun isValidUserOrEmail(input: String): Boolean{
        return Regex("^([A-Za-z]{3,10}|[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+)\$").matches(input)
    }

    fun isValidPassword(input: String): Boolean{
        return Regex("^[A-Za-z0-9@#_]{8,16}\$").matches(input)
    }
}