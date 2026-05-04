package com.yjotdev.login.domain.usecase.config

import javax.inject.Inject
import com.yjotdev.login.domain.repository.ConfigRepository

class SaveConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    /** Guarda id de usuario **/
    operator fun invoke(user: MutableMap<String, String>) {
        configRepository.saveConfig(user)
    }
}