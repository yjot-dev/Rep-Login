package com.yjotdev.login.data.local.dao

import androidx.room.Dao
import androidx.room.Update
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.yjotdev.login.data.local.entity.UserEntity

/**
 * Interfaz DAO (Data Access Object) para Room.
 * Esta es la implementación concreta del puerto del dominio usando la tecnología Room.
 * Pertenece a la capa de Infraestructura.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun getLocalUser(): Flow<UserEntity?>
    @Insert
    suspend fun insertLocalUser(user: UserEntity)
    @Update
    suspend fun updateLocalUser(user: UserEntity)
    @Delete
    suspend fun deleteLocalUser(user: UserEntity)
}