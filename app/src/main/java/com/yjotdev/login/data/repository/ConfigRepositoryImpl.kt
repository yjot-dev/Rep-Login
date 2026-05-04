package com.yjotdev.login.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.repository.ConfigRepository

/**
 * Implementación del ConfigRepository.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza SharedPreferences para guardar y obtener datos locales del usuario.
 */
@Singleton
class ConfigRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
) : ConfigRepository {
    private val sp = context.getSharedPreferences("MyConfig", Context.MODE_PRIVATE)

    override fun saveConfig(user: MutableMap<String, String>) {
        sp.edit().apply {
            putString("token", user["token"])
            apply()
        }
    }

    override fun getConfig() = mutableMapOf(
        "token" to sp.getString("token", ""),
    )
}