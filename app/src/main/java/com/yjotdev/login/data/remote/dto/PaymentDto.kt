package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PaymentDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("monto") val amount: Float = 0f,
    @SerializedName("moneda") val money: String = "",
    @SerializedName("fecha") val date: String = "",
    @SerializedName("estado") val status: Int = 0,
    @SerializedName("purchase_token") val purchaseToken: String = "",
    @SerializedName("usuario_id") val userId: Int = 0
)