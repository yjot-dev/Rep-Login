package com.yjotdev.login.domain.entity

import com.google.gson.annotations.SerializedName

data class EmailEntity(
    @SerializedName("para") val to: String = "",
    @SerializedName("asunto") val subject: String = "",
    @SerializedName("mensaje") val text: String = ""
)