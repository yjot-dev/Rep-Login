package com.yjotdev.login.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yjotdev.login.data.local.entity.UserEntity
import com.yjotdev.login.data.local.entity.NotificationEntity
import com.yjotdev.login.data.local.entity.PaymentEntity
import com.yjotdev.login.data.local.dao.UserDao
import com.yjotdev.login.data.local.dao.NotificationDao
import com.yjotdev.login.data.local.dao.PaymentDao

@Database(
    entities = [UserEntity::class, NotificationEntity::class, PaymentEntity::class],
    version = 2,
    exportSchema = false
)
abstract class GupnDatabase: RoomDatabase() {

    companion object {
        const val NAME = "bd_gupn"
    }

    abstract fun userDao(): UserDao

    abstract fun notificationDao(): NotificationDao

    abstract fun paymentDao(): PaymentDao
}