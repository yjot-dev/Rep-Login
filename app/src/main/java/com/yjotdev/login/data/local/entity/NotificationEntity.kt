package com.yjotdev.login.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String = "",
    val date: String = "",
    val userId: Int = 0
)