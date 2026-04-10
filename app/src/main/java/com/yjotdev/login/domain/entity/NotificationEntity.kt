package com.yjotdev.login.domain.entity

import com.google.gson.annotations.SerializedName

data class NotificationEntity(
    @SerializedName("id") val id: String = "",
    @SerializedName("mensaje") val message: String = "",
    @SerializedName("fecha") val date: String = ""
)