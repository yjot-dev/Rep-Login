package com.yjotdev.login.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Float = 0f,
    val money: String = "",
    val status: String = "",
    val date: String = "",
    val userId: Int = 0
)