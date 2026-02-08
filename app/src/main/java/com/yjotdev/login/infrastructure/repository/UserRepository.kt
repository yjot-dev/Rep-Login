package com.yjotdev.login.infrastructure.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.entity.LoginEntity
import com.yjotdev.login.domain.entity.RecoveryEntity
import com.yjotdev.login.domain.port.UserPort
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.infrastructure.network.client.Api
import com.yjotdev.login.infrastructure.network.core.safeApiCallForBody
import com.yjotdev.login.infrastructure.network.core.safeApiCallForUnit
/**
 * Implementación del UserPort.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class UserRepository @Inject constructor(
    private val api: Api
) : UserPort {
    override suspend fun findUser(login: LoginEntity): Result<UserEntity> {
        return safeApiCallForBody { api.getUserRetrofit().findUser(login) }
    }

    override suspend fun changePasswordUser(recovery: RecoveryEntity): Result<Unit> {
        return safeApiCallForUnit { api.getUserRetrofit().changePasswordUser(recovery) }
    }

    override suspend fun insertUser(user: UserEntity): Result<Unit> {
        return safeApiCallForUnit { api.getUserRetrofit().insertUser(user) }
    }

    override suspend fun updateUser(id: Int, user: UserEntity): Result<Unit> {
        return safeApiCallForUnit { api.getUserRetrofit().updateUser(id, user) }
    }

    override suspend fun deleteUser(id: Int): Result<Unit> {
        return safeApiCallForUnit { api.getUserRetrofit().deleteUser(id) }
    }
}