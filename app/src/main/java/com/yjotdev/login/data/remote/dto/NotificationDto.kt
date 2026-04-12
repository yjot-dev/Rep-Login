package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("mensaje") val message: String = "",
    @SerializedName("fecha") val date: String = "",
    @SerializedName("idf") val idf: Int = 0
)