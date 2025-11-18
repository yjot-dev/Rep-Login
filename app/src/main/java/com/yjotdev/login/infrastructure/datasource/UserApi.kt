package com.yjotdev.login.infrastructure.datasource

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.PATCH
import com.yjotdev.login.domain.entity.UserEntity

/**
 * Interfaz de Retrofit para las operaciones de la API de usuarios.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface UserApi {
    @POST("users/login")
    suspend fun findUser(@Body user: UserEntity): Response<UserEntity>

    @PATCH("users")
    suspend fun changePasswordUser(@Body user: UserEntity): Response<Unit>

    @POST("users")
    suspend fun insertUser(@Body user: UserEntity): Response<Unit>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserEntity): Response<Unit>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>
}