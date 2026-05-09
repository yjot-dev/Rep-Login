package com.yjotdev.login.data.remote.network

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.BuildConfig
import com.yjotdev.login.data.remote.core.NullOnEmptyConverterFactory

@Singleton
class RetrofitBuilder @Inject constructor() {
    // Configuración dinámica de URL según el entorno
    private val baseUrl = if (BuildConfig.DEBUG) { "http://192.168.1.20:3000/api/" }
                      else { "https://servicio-api-login-386835133500.us-central1.run.app/api/" }

    // Configuración de log interceptor
    private val loggingInterceptor = HttpLoggingInterceptor{ msm ->
        Log.d("Https", msm)
    }.apply { level = HttpLoggingInterceptor.Level.BODY }

    // Configuración de cliente HTTP
    private val client = okhttp3.OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Construye y devuelve una instancia configurada de Retrofit.
     * Este es ahora el único punto de configuración de red para toda la app.
     */
    fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .build()
}