package com.actividad_22.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Client::class], version = 1)
abstract class ClientDataBase : RoomDatabase() {
    abstract fun clientDao(): ClientDAO
}