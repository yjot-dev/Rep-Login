package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateOrderRequestDto(
    @SerializedName("plan") val plan: String = "",
    @SerializedName("userId") val userId: Int = 0,
    @SerializedName("moneyCode") val moneyCode: String = ""
)