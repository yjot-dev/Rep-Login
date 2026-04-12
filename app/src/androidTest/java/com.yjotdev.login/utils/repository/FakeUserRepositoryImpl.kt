package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserRepository

@Singleton
class FakeUserRepositoryImpl @Inject constructor() : UserRepository {

    override suspend fun findUser(login: LoginModel): Result<UserModel> {
        return if (login != LoginModel()){
            Result.Success(UserModel())
        }else {
            Result.Error(Exception("Error al encontrar el usuario"))
        }
    }

    override suspend fun changePasswordUser(recovery: RecoveryModel): Result<Unit> {
        return if (recovery != RecoveryModel()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al cambiar la contraseña"))
        }
    }

    override suspend fun insertUser(user: UserModel): Result<Unit> {
        return if (user != UserModel()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al insertar el usuario"))
        }
    }

    override suspend fun updateUser(id: Int, user: UserModel): Result<Unit> {
        return if (user != UserModel()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al actualizar el usuario"))
        }
    }

    override suspend fun deleteUser(id: Int): Result<Unit> {
        return if (id != 0){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al eliminar el usuario"))
        }
    }
}