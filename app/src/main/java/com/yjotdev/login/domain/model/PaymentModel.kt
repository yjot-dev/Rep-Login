package com.yjotdev.login.domain.model

data class PaymentModel(
    val id: Int = 0,
    val amount: String = "",
    val date: String = "",
    val status: String = "",
    val idf: Int = 0
)