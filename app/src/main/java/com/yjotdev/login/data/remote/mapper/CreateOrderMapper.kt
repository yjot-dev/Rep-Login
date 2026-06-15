package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.CreateOrderRequestDto
import com.yjotdev.login.data.remote.dto.CreateOrderResponseDto
import com.yjotdev.login.domain.model.CreateOrderRequestModel
import com.yjotdev.login.domain.model.CreateOrderResultModel

fun CreateOrderRequestModel.toDto() = CreateOrderRequestDto(
    plan = this.plan,
    userId = this.userId,
    moneyCode = this.moneyCode
)

fun CreateOrderResponseDto.toDomain() = CreateOrderResultModel(
    approveUrl = this.approveUrl
)