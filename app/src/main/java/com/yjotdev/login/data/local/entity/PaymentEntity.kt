package com.yjotdev.login.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "payment",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Float = 0f,
    val money: String = "",
    val date: String = "",
    val status: Int = 0,
    val purchaseToken: String = "",
    val userId: Int = 0
)