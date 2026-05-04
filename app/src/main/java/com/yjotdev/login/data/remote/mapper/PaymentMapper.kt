package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.PaymentDto
import com.yjotdev.login.domain.model.PaymentModel

/**
 * Mapea el objeto de red (DTO) al modelo de negocio (Domain).
 */
fun PaymentDto.toDomain() = PaymentModel(
    id = this.id,
    amount = this.amount,
    money = this.money,
    date = this.date,
    status = this.status,
    userId = this.userId
)

/**
 * Mapea el modelo de negocio (Domain) al objeto de red (DTO) para enviar a la API.
 */
fun PaymentModel.toDto() = PaymentDto(
    id = this.id,
    amount = this.amount,
    money = this.money,
    date = this.date,
    status = this.status,
    userId = this.userId
)