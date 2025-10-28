package com.actividad_22.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDAO {

    @Query("SELECT * FROM client")
    fun findAll(): Flow<List<Client>>

    @Query("SELECT * FROM client WHERE email_client = :email AND password_client = :password")
    fun findClient(email:String, password:String): Flow<List<Client>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client)

    @Update
    suspend fun updateClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

}