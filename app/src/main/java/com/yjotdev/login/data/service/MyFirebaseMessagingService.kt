package com.yjotdev.login.data.service

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yjotdev.login.domain.usecase.config.SaveConfigUseCase
import com.yjotdev.login.R

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var saveConfigUseCase: SaveConfigUseCase

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Construye y muestra la notificación
        val title = remoteMessage.notification?.title ?: "Aviso"
        val body = remoteMessage.notification?.body ?: "Tienes una nueva notificación"

        val notificationBuilder = NotificationCompat.Builder(this, "default_channel")
            .setSmallIcon(R.drawable.notifications_70)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(1001, notificationBuilder.build())
        }
    }

    override fun onNewToken(token: String) {
        // Guarda token en local
        saveConfigUseCase(mutableMapOf("token" to token))
    }
}