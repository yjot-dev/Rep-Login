package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.LoginDto
import com.yjotdev.login.domain.model.LoginModel

/**
 * Mapea el objeto de red (DTO) al modelo de negocio (Domain).
 */
fun LoginDto.toDomain() = LoginModel(
    name = this.name,
    password = this.password
)

/**
 * Mapea el modelo de negocio (Domain) al objeto de red (DTO) para enviar a la API.
 */
fun LoginModel.toDto() = LoginDto(
    name = this.name,
    password = this.password
)