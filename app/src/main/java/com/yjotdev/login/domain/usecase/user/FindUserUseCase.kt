package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserRepository

class FindUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /** Obtener usuario mediante caso de uso **/
    suspend operator fun invoke(login: LoginModel): Result<UserModel> {
        return userRepository.findUser(login)
    }
}