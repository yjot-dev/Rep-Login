package com.yjotdev.login.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.domain.repository.StringRepository

/**
 * Implementación del StringRepository.
 * Esta clase pertenece a la capa de Infraestructura.
 * Utiliza la Api (Retrofit) para obtener los datos y los traduce a objetos
 * que la capa de Dominio entiende (Result<T>).
 */
@Singleton
class StringRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StringRepository {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }
}