package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserApiRepository

class GetRemoteUserUseCase @Inject constructor(
    private val userApiRepository: UserApiRepository
) {
    /** Obtener usuario mediante caso de uso **/
    suspend operator fun invoke(login: LoginModel): Result<UserModel> {
        return userApiRepository.getRemoteUser(login)
    }
}