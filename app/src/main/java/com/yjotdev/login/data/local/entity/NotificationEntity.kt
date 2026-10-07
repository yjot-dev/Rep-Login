package com.yjotdev.login.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "notification",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String = "",
    val date: String = "",
    val userId: Int = 0
)
