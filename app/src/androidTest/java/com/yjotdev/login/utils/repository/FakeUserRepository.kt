package com.yjotdev.login.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.port.UserPort
import com.yjotdev.login.domain.entity.UserEntity

@Singleton
class FakeUserRepository @Inject constructor() : UserPort {
    override suspend fun findUser(name: String, email: String, password: String): Result<UserEntity> {
        val user = UserEntity(0, name, email, password)
        return if (user != UserEntity()){
            Result.Success(user)
        }else {
            Result.Error(Exception("Error al encontrar el usuario"))
        }
    }

    override suspend fun changePasswordUser(email: String, password: String): Result<Unit> {
        val user = UserEntity(0, "", email, password)
        return if (user != UserEntity()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al cambiar la contraseña"))
        }
    }

    override suspend fun insertUser(user: UserEntity): Result<Unit> {
        return if (user != UserEntity()){
            Result.Success(Unit)
        }else {
            Result.Error(Exception("Error al insertar el usuario"))
        }
    }

    override suspend fun updateUser(id: Int, user: UserEntity): Result<Unit> {
        return if (user != UserEntity()){
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