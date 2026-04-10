package com.yjotdev.login.domain.entity

import com.google.gson.annotations.SerializedName

data class PaymentEntity(
    @SerializedName("id") val id: String = "",
    @SerializedName("monto") val amount: String = "",
    @SerializedName("fecha") val date: String = "",
    @SerializedName("estado") val status: String = ""
)