package com.yjotdev.login.domain.usecase.config

import javax.inject.Inject
import com.yjotdev.login.domain.repository.ConfigRepository

class GetConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    /** Obtiene id de usuario **/
    operator fun invoke(): MutableMap<String, String?> {
        return configRepository.getConfig()
    }
}