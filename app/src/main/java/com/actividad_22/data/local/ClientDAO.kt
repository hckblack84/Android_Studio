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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client)

    @Update
    suspend fun updateClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Query("DELETE FROM client")
    suspend fun clearClientsTable()


    @Query("SELECT * FROM client WHERE email_client = :email AND password_client = :password LIMIT 1")
    suspend fun login(email: String, password: String): Client?

    @Query("SELECT * FROM client WHERE id_client = :id LIMIT 1")
    suspend fun getClientById(id: Long): Client?

    @Query("SELECT * FROM client WHERE email_client = :email LIMIT 1")
    suspend fun getClientByEmail(email: String): Client?
}
