package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.port.UserPort

@Singleton
class ChangePasswordUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    /** Actualizar clave de usuario mediante caso de uso **/
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return userPort.changePasswordUser(email, password)
    }
}