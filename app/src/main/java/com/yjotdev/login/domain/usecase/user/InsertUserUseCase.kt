package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.port.UserPort

@Singleton
class InsertUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    /** Insertar usuario mediante caso de uso **/
    suspend operator fun invoke(user: UserEntity): Result<Unit> {
        return userPort.insertUser(user)
    }
}