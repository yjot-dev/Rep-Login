package com.yjotdev.login.data.remote.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.yjotdev.login.data.remote.dto.EmailDto

/**
 * Interfaz de Retrofit para las operaciones de la API de emails.
 * ESTA interfaz pertenece a la capa de Infraestructura y define los endpoints HTTP.
 */
interface EmailService {
    @POST("oauth/email")
    suspend fun sendEmail(@Body email: EmailDto): Response<Unit>
}