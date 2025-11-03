package com.actividad_22.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {
    @Query("SELECT * FROM Product")
    fun findAll(): Flow<List<ProductData>>

    // wuery para filtrar por categoria
    @Query("SELECT * FROM Product WHERE category_product = :category")
    suspend fun findByCategory(category: Int): List<ProductData>

    @Query("DELETE FROM Product WHERE id_product = :id")
    suspend fun deleteProductById(id:Long)

    @Query("DELETE FROM Product")
    suspend fun truncateProducts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productData: ProductData)

    @Update
    suspend fun updateProduct(productData: ProductData)

    @Delete
    suspend fun deleteProduct(productData: ProductData)
}