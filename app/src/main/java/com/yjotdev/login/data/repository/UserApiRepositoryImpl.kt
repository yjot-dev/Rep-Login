package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.repository.UserApiRepository
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.data.remote.core.safeApiCallForBody
import com.yjotdev.login.data.remote.core.safeApiCallForUnit
import com.yjotdev.login.data.remote.mapper.toDomain
import com.yjotdev.login.data.remote.mapper.toDto
import com.yjotdev.login.data.remote.api.UserApi
import com.yjotdev.login.domain.core.mapSuccess

/**
 * Implementación del UserRepository.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class UserApiRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserApiRepository {

    override suspend fun getRemoteUser(login: LoginModel): Result<UserModel> {
        return safeApiCallForBody { userApi.getRemoteUser(login.toDto()) }
            .mapSuccess { result -> result.toDomain() }
    }

    override suspend fun setPasswordRemoteUser(recovery: RecoveryModel): Result<Unit> {
        return safeApiCallForUnit { userApi.setPasswordRemoteUser(recovery.toDto()) }
    }

    override suspend fun insertRemoteUser(user: UserModel): Result<Unit> {
        return safeApiCallForUnit { userApi.insertRemoteUser(user.toDto()) }
    }

    override suspend fun updateRemoteUser(id: Int, user: UserModel): Result<Unit> {
        return safeApiCallForUnit { userApi.updateRemoteUser(id, user.toDto()) }
    }

    override suspend fun deleteRemoteUser(id: Int): Result<Unit> {
        return safeApiCallForUnit { userApi.deleteRemoteUser(id) }
    }
}