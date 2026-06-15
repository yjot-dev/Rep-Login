package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CaptureOrderRequestDto(
    @SerializedName("orderId") val orderId: String = ""
)