package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ValidateDto(
    @SerializedName("purchaseToken") val purchaseToken: String = "",
    @SerializedName("productId") val productId: String? = null,
    @SerializedName("userId") val userId: Int = 0,
    @SerializedName("amount") val amount: Float = 0f,
    @SerializedName("money") val money: String = "",
    @SerializedName("date") val date: String = ""
)