package com.yjotdev.login.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept","application/json")
            .addHeader("Content-Type","application/json")
        // Si hay token disponible, añadirlo
        TokenProvider.firebaseToken?.let { token ->
            request.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}