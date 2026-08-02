package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.ValidateDto
import com.yjotdev.login.domain.model.ValidateModel

fun ValidateDto.toDomain() = ValidateModel(
    purchaseToken = this.purchaseToken,
    productId = this.productId,
    userId = this.userId,
    amount = this.amount,
    money = this.money,
    date = this.date
)

fun ValidateModel.toDto() = ValidateDto(
    purchaseToken = this.purchaseToken,
    productId = this.productId,
    userId = this.userId,
    amount = this.amount,
    money = this.money,
    date = this.date
)