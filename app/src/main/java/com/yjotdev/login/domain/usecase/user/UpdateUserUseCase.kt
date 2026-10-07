package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserApiRepository

class UpdateUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    /** Actualizar usuario mediante caso de uso **/
    suspend operator fun invoke(id: Int, user: UserModel): Result<Unit> {
        return userApiRepository.updateUser(id, user)
    }
}