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
    private val url = "https://api-login-production-f93c.up.railway.app/api/"
    private val httpsClient = if (BuildConfig.DEBUG) { Client.getUnsafeClient(context) }
    else { Client.getSafeClient() }

    fun getUserRetrofit(): UserApi = Retrofit.Builder()
        .baseUrl(url)
        .client(httpsClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserApi::class.java)

    fun getEmailRetrofit(): EmailApi = Retrofit.Builder()
        .baseUrl(url)
        .client(httpsClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmailApi::class.java)
}