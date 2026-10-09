package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserApiRepository

@Singleton
class FakeUserApiRepositoryImpl @Inject constructor() : UserApiRepository {

    override suspend fun getRemoteUser(login: LoginModel): Result<UserModel> {
        return if (login != LoginModel()) {
            Result.Success(
                UserModel(
                    id = 1,
                    name = if (login.name.isNotEmpty()) login.name else "Invitado",
                    email = "john.mckinley@examplepetstore.com",
                    password = login.password
                )
            )
        } else {
            Result.Error(Exception("Error al encontrar el usuario"))
        }
    }

    override suspend fun setPasswordRemoteUser(recovery: RecoveryModel): Result<Unit> {
        return if (recovery != RecoveryModel()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al cambiar la contraseña"))
        }
    }

    override suspend fun insertRemoteUser(user: UserModel): Result<Unit> {
        return if (user != UserModel()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al insertar el usuario"))
        }
    }

    override suspend fun updateRemoteUser(id: Int, user: UserModel): Result<Unit> {
        return if (user != UserModel()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al actualizar el usuario"))
        }
    }

    override suspend fun deleteRemoteUser(id: Int): Result<Unit> {
        return if (id != 0) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al eliminar el usuario"))
        }
    }
}