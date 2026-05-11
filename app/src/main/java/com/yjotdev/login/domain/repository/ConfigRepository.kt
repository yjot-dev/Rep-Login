package com.yjotdev.login.domain.repository

interface ConfigRepository {
    /**
     * Guardar token de Firebase Cloud Messaging (FCM)
     */
    fun saveTokenFCM(token: String)
    /**
     * Obtener todas las configuraciones guardadas
     */
    fun getConfig(): MutableMap<String, String?>
}