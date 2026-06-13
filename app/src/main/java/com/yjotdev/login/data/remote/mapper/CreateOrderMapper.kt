package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.CreateOrderRequestDto
import com.yjotdev.login.domain.model.CreateOrderRequestModel

fun CreateOrderRequestModel.toDto() = CreateOrderRequestDto(
    plan = this.plan,
    userId = this.userId,
    moneyCode = this.moneyCode
)