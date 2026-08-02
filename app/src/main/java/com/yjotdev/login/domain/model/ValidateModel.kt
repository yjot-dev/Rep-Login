package com.yjotdev.login.domain.model

data class ValidateModel(
    val purchaseToken: String = "",
    val productId: String? = null,
    val userId: Int = 0,
    val amount: Float = 0f,
    val money: String = "",
    val date: String = ""
)