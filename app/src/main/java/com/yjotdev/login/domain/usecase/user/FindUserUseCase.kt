package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.port.UserPort

@Singleton
class FindUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    /** Obtener usuario mediante caso de uso **/
    suspend operator fun invoke(name: String, email: String, password: String): Result<UserEntity> {
        return userPort.findUser(name, email, password)
    }
}