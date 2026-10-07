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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayments(payments: List<PaymentEntity>)

    @Query("""
        SELECT * FROM payment 
        WHERE userId = :userId
        ORDER BY id DESC 
        LIMIT :limit
    """)
    fun getPaymentsByUserId(userId: Int, limit: Int): Flow<List<PaymentEntity>>
}