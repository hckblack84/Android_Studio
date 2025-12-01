package com.actividad_22.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile
@Database(entities = [ProductData::class], version = 4, exportSchema = false)
abstract class ProductDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDAO



companion object {
    @Volatile
    private var INTANCE: ProductDataBase? = null

    fun getDataBase(context: Context): ProductDataBase {
        return INTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ProductDataBase::class.java,
                "product_database"
            ).fallbackToDestructiveMigration()
                .build()
            INTANCE = instance
            instance

        }
    }
}
}