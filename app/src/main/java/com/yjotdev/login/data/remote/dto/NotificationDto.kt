package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("mensaje") val message: String = "",
    @SerializedName("fecha") val date: String = "",
    @SerializedName("usuario_id") val userId: Int = 0
)