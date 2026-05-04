package com.yjotdev.login.domain.model

data class SendNotificationRequestModel(
    val userId: Int = 0,
    val token: String = "",
    val title: String = "",
    val body: String = "",
)