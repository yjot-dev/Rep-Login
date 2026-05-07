package com.yjotdev.login.domain.repository

interface ConfigRepository {
    /**
     * Guardar configuracion de usuario
     */
    fun saveConfig(settings: MutableMap<String, String>)
    /**
     * Obtener configuracion de usuario
     */
    fun getConfig(): MutableMap<String, String?>
}