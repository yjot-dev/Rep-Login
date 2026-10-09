package com.yjotdev.login.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.yjotdev.login.data.local.entity.PaymentEntity

/**
 * Interfaz DAO (Data Access Object) para Room.
 * Esta es la implementación concreta del puerto del dominio usando la tecnología Room.
 * Pertenece a la capa de Infraestructura.
 */
@Dao
interface PaymentDao {
    @Query("""
        SELECT * FROM payment 
        WHERE userId = :userId
        ORDER BY id DESC 
        LIMIT :limit
    """)
    fun getLocalPayments(userId: Int, limit: Int): Flow<List<PaymentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalPayments(payments: List<PaymentEntity>)

    @Query("DELETE FROM payment WHERE userId = :userId")
    suspend fun deleteLocalPayments(userId: Int)
}