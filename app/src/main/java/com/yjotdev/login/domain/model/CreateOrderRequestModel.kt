package com.yjotdev.login.domain.model

data class CreateOrderRequestModel(
    val plan: String = "",
    val userId: Int = 0,
    val countryCode: String = ""
)