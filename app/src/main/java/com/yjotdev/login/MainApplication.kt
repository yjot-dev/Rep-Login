package com.yjotdev.login

import android.app.Application
import android.os.Build
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        // Crear canal de notificación (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                "default_channel",
                "Default Channel",
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(android.app.NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}