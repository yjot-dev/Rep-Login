package com.yjotdev.login.infrastructure.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.yjotdev.login.domain.entity.EmailEntity

/**
 * Interfaz de Retrofit para las operaciones de la API de emails.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface EmailApi {
    @POST("oauth/email")
    suspend fun sendEmail(@Body email: EmailEntity): Response<Unit>
}