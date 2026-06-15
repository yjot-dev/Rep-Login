package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateOrderResponseDto(
    @SerializedName("approveUrl") val approveUrl: String = ""
)