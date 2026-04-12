package com.yjotdev.login.data.remote.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.BuildConfig
import com.yjotdev.login.data.remote.core.NullOnEmptyConverterFactory

@Singleton
class RetrofitBuilder @Inject constructor(
    @ApplicationContext context: Context
) {
    // Configuración dinámica de URL según el entorno
    private val url = if (BuildConfig.DEBUG) { "https://192.168.1.20:3000/api/" }
                      else { "https://servicio-api-login-386835133500.us-central1.run.app/api/" }
    // Configuración del cliente HTTP (Seguro o No Seguro)
    private val httpsClient = if (BuildConfig.DEBUG) { OkHttpClient.getUnsafeClient(context) }
                              else { OkHttpClient.getSafeClient() }

    /**
     * Construye y devuelve una instancia configurada de Retrofit.
     * Este es ahora el único punto de configuración de red para toda la app.
     */
    fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(httpsClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .build()
}