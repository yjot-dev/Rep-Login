package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserRepository

class InsertUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /** Insertar usuario mediante caso de uso **/
    suspend operator fun invoke(user: UserModel): Result<Unit> {
        return userRepository.insertUser(user)
    }
}