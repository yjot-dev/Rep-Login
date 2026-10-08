package com.yjotdev.login.data.local.mapper

import com.yjotdev.login.data.local.entity.PaymentEntity
import com.yjotdev.login.domain.model.PaymentModel

fun PaymentEntity.toDomain() = PaymentModel(
    id = this.id,
    amount = this.amount,
    money = this.money,
    date = this.date,
    status = this.status,
    userId = this.userId
)

fun PaymentModel.toBD() = PaymentEntity(
    id = this.id,
    amount = this.amount,
    money = this.money,
    date = this.date,
    status = this.status,
    userId = this.userId
)