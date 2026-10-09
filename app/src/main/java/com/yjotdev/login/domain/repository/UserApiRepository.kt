package com.yjotdev.login.domain.repository

import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.core.Result

/**
 * Define el contrato para las operaciones del repositorio de usuarios.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve tipos de datos del Dominio (UserModel) o resultados encapsulados.
 */
interface UserApiRepository {
    /**
     * Busca un usuario basado en sus credenciales.
     * @return Result<UserModel> que contiene el usuario si se encuentra, o un error.
     */
    suspend fun getRemoteUser(login: LoginModel): Result<UserModel>

    /**
     * Actualiza la clave de un usuario existente.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun setPasswordRemoteUser(recovery: RecoveryModel): Result<Unit>

    /**
     * Inserta un nuevo usuario en la fuente de datos.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun insertRemoteUser(user: UserModel): Result<Unit>

    /**
     * Actualiza un usuario existente.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun updateRemoteUser(id: Int, user: UserModel): Result<Unit>

    /**
     * Elimina un usuario por su ID.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun deleteRemoteUser(id: Int): Result<Unit>
}