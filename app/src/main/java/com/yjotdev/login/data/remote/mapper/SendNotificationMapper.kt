package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.SendNotificationRequestDto
import com.yjotdev.login.domain.model.SendNotificationRequestModel

fun SendNotificationRequestModel.toDto() = SendNotificationRequestDto(
    userId = this.userId,
    token = this.token,
    title = this.title,
    body = this.body
)