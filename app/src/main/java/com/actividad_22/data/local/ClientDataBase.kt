package com.actividad_22.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

//DATABASE AS A SINGLETON
@Database(entities = [Client::class], version = 1)
abstract class ClientDataBase : RoomDatabase() {
    abstract fun clientDao(): ClientDAO

    companion object{
        @Volatile
        private var INSTANCE: ClientDataBase? = null

        fun getDataBase(context: Context): ClientDataBase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClientDataBase::class.java,
                    "client_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}