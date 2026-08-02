package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SendNotificationRequestDto(
    @SerializedName("userId") val userId: Int = 0,
    @SerializedName("token") val token: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("body") val body: String = "",
    @SerializedName("date") val date: String = ""
)