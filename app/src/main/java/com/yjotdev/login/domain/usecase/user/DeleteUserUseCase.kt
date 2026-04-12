package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.repository.UserRepository

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /** Eliminar usuario mediante caso de uso **/
    suspend operator fun invoke(id: Int): Result<Unit> {
        return userRepository.deleteUser(id)
    }
}