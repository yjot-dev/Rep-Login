package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.repository.UserRepository
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.data.remote.core.safeApiCallForBody
import com.yjotdev.login.data.remote.core.safeApiCallForUnit
import com.yjotdev.login.data.remote.mapper.toDomain
import com.yjotdev.login.data.remote.mapper.toDto
import com.yjotdev.login.data.remote.service.UserService
import com.yjotdev.login.domain.core.mapSuccess

/**
 * Implementación del UserRepository.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun findUser(login: LoginModel): Result<UserModel> {
        return safeApiCallForBody { userService.findUser(login.toDto()) }
            .mapSuccess { result -> result.toDomain() }
    }

    override suspend fun changePasswordUser(recovery: RecoveryModel): Result<Unit> {
        return safeApiCallForUnit { userService.changePasswordUser(recovery.toDto()) }
    }

    override suspend fun insertUser(user: UserModel): Result<Unit> {
        return safeApiCallForUnit { userService.insertUser(user.toDto()) }
    }

    override suspend fun updateUser(id: Int, user: UserModel): Result<Unit> {
        return safeApiCallForUnit { userService.updateUser(id, user.toDto()) }
    }

    override suspend fun deleteUser(id: Int): Result<Unit> {
        return safeApiCallForUnit { userService.deleteUser(id) }
    }
}