package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateOrderRequestDto(
    @SerializedName("plan") val plan: String = ""
)

data class CreateOrderResponseDto(
    @SerializedName("id") val id: String = ""
)