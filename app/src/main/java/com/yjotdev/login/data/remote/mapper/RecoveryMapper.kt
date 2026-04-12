package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.RecoveryDto
import com.yjotdev.login.domain.model.RecoveryModel

/**
 * Mapea el objeto de red (DTO) al modelo de negocio (Domain).
 */
fun RecoveryDto.toDomain() = RecoveryModel(
    email = this.email,
    password = this.password
)

/**
 * Mapea el modelo de negocio (Domain) al objeto de red (DTO) para enviar a la API.
 */
fun RecoveryModel.toDto() = RecoveryDto(
    email = this.email,
    password = this.password
)