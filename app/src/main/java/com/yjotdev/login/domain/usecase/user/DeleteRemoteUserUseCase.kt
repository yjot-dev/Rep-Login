package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.repository.UserApiRepository

class DeleteRemoteUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    /** Eliminar usuario mediante caso de uso **/
    suspend operator fun invoke(id: Int): Result<Unit> {
        return userApiRepository.deleteRemoteUser(id)
    }
}