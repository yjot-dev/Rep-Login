package com.yjotdev.login.domain.usecase.config

import javax.inject.Inject
import com.yjotdev.login.domain.repository.ConfigRepository

class SaveTokenFcmUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    /** Guarda token FCM **/
    operator fun invoke(token: String) {
        configRepository.saveTokenFCM(token)
    }
}