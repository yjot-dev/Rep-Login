package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.NotificationDto
import com.yjotdev.login.domain.model.NotificationModel

/**
 * Mapea el objeto de red (DTO) al modelo de negocio (Domain).
 */
fun NotificationDto.toDomain() = NotificationModel(
    id = this.id,
    message = this.message,
    date = this.date,
    idf = this.idf
)

/**
 * Mapea el modelo de negocio (Domain) al objeto de red (DTO) para enviar a la API.
 */
fun NotificationModel.toDto() = NotificationDto(
    id = this.id,
    message = this.message,
    date = this.date,
    idf = this.idf
)