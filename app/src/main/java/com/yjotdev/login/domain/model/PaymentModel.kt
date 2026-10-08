package com.yjotdev.login.domain.model

data class PaymentModel(
    val id: Int = 0,
    val amount: Float = 0f,
    val money: String = "",
    val status: String = "",
    val date: String = "",
    val userId: Int = 0
)