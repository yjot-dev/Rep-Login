package com.yjotdev.login.infrastructure.network.client

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.login.BuildConfig
import com.yjotdev.login.infrastructure.network.api.EmailApi
import com.yjotdev.login.infrastructure.network.api.UserApi
import com.yjotdev.login.infrastructure.network.core.NullOnEmptyConverterFactory

@Singleton
class Api @Inject constructor(
    @ApplicationContext context: Context
) {
    private val url = if (BuildConfig.DEBUG) { "https://192.168.1.20:3000/api/" }
                      else { "https://servicio-api-login-386835133500.us-central1.run.app/api/" }
    private val httpsClient = if (BuildConfig.DEBUG) { Client.getUnsafeClient(context) }
                              else { Client.getSafeClient() }

    /** API Tabla Usuario **/
    fun getUserRetrofit(): UserApi = Retrofit.Builder()
        .baseUrl(url)
        .client(httpsClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .build()
        .create(UserApi::class.java)

    /** API Gmail **/
    fun getEmailRetrofit(): EmailApi = Retrofit.Builder()
        .baseUrl(url)
        .client(httpsClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .build()
        .create(EmailApi::class.java)
}