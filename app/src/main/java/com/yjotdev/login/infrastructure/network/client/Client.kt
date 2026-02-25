package com.yjotdev.login.infrastructure.network.client

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import com.yjotdev.login.R

object Client {
    private val loggingInterceptor = HttpLoggingInterceptor{ msm ->
        Log.d("Https", msm)
    }.apply { level = HttpLoggingInterceptor.Level.BODY }

    /** Cliente para app en producción **/
    fun getSafeClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    /** Cliente para app en desarrollo **/
    fun getUnsafeClient(context: Context): OkHttpClient {
        return try {
            // Leer el certificado desde res/raw
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val inputStream: InputStream = context.resources.openRawResource(R.raw.mycert)
            val certificate = certificateFactory.generateCertificate(inputStream)
            inputStream.close()
            // Cargar el KeyStore con certificados confiables (si tienes un certificado personalizado, cámbialo aquí)
            val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                load(null, null) // Inicia un KeyStore vacío
                setCertificateEntry("my_certificate", certificate)
            }
            // Inicializar TrustManagerFactory con el KeyStore
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore)
            }
            // Obtener el TrustManager
            val trustManager = tmf.trustManagers[0] as X509TrustManager
            // Crear un SSLContext con el TrustManager
            val sslContext = SSLContext.getInstance("TLS").apply {
                init(null, arrayOf(trustManager), null)
            }
            // Construye el cliente OkHttp
            OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustManager)
                .hostnameVerifier { _, _ -> true }
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}