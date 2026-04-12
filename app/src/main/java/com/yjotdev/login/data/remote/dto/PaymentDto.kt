package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PaymentDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("monto") val amount: String = "",
    @SerializedName("fecha") val date: String = "",
    @SerializedName("estado") val status: String = "",
    @SerializedName("idf") val idf: Int = 0
)