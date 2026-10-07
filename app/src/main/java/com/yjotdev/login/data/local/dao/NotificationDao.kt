package com.yjotdev.login.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.yjotdev.login.data.local.entity.NotificationEntity

/**
 * Interfaz DAO (Data Access Object) para Room.
 * Esta es la implementación concreta del puerto del dominio usando la tecnología Room.
 * Pertenece a la capa de Infraestructura.
 */
@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Query("""
        SELECT * FROM notification 
        WHERE userId = :userId 
        ORDER BY id DESC 
        LIMIT :limit
    """)
    fun getNotificationsByUserId(userId: Int, limit: Int): Flow<List<NotificationEntity>>
}