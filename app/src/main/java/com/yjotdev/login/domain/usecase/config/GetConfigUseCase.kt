package com.yjotdev.login.domain.usecase.config

import javax.inject.Inject
import com.yjotdev.login.domain.repository.ConfigRepository

class GetConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    /** Obtiene todas las configuraciones mediante caso de uso **/
    operator fun invoke(): MutableMap<String, String?> {
        return configRepository.getConfig()
    }
}