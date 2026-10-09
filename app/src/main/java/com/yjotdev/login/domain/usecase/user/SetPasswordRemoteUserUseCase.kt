package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.repository.UserApiRepository

class SetPasswordRemoteUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    /** Actualizar clave de usuario mediante caso de uso **/
    suspend operator fun invoke(recovery: RecoveryModel): Result<Unit> {
        return userApiRepository.setPasswordRemoteUser(recovery)
    }
}