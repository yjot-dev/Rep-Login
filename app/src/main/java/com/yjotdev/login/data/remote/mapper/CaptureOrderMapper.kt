package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.CaptureOrderRequestDto
import com.yjotdev.login.domain.model.CaptureOrderRequestModel

fun CaptureOrderRequestModel.toDto() = CaptureOrderRequestDto(
    orderId = this.orderId
)