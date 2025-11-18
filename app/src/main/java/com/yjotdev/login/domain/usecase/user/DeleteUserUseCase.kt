package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.port.UserPort

@Singleton
class DeleteUserUseCase @Inject constructor(
    private val userPort: UserPort
) {
    /** Eliminar usuario mediante caso de uso **/
    suspend operator fun invoke(id: Int): Result<Unit> {
        return userPort.deleteUser(id)
    }
}