package com.yjotdev.login.domain.port

import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.core.Result

/**
 * Define el contrato para las operaciones del repositorio de usuarios.
 * Esta interfaz pertenece a la capa de Dominio. No conoce Retrofit ni detalles de la API.
 * Devuelve tipos de datos del Dominio (UserEntity) o resultados encapsulados.
 */
interface UserPort {
    /**
     * Busca un usuario basado en sus credenciales.
     * @return Result<UserEntity> que contiene el usuario si se encuentra, o un error.
     */
    suspend fun findUser(name: String, email: String, password: String): Result<UserEntity>

    /**
     * Actualiza la clave de un usuario existente.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun changePasswordUser(email: String, password: String): Result<Unit>

    /**
     * Inserta un nuevo usuario en la fuente de datos.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun insertUser(user: UserEntity): Result<Unit>

    /**
     * Actualiza un usuario existente.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun updateUser(id: Int, user: UserEntity): Result<Unit>

    /**
     * Elimina un usuario por su ID.
     * @return Result<Unit> que indica éxito o un error.
     */
    suspend fun deleteUser(id: Int): Result<Unit>
}