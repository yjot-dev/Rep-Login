package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.EmailDto
import com.yjotdev.login.domain.model.EmailModel

/**
 * Mapea el objeto de red (DTO) al modelo de negocio (Domain).
 */
fun EmailDto.toDomain() = EmailModel(
    to = this.to,
    subject = this.subject,
    text = this.text
)

/**
 * Mapea el modelo de negocio (Domain) al objeto de red (DTO) para enviar a la API.
 */
fun EmailModel.toDto() = EmailDto(
    to = this.to,
    subject = this.subject,
    text = this.text
)