package com.actividad_22.data.repository

import com.actividad_22.data.local.ProductData
import com.actividad_22.data.local.ProductDAO
import kotlinx.coroutines.flow.Flow

class ProductRepository(val productDAO: ProductDAO) {

    fun getAllProducts(): Flow<List<ProductData>> = productDAO.findAll()

    suspend fun getProductsByCategory(category: Int): List<ProductData> {
        // Necesitarás agregar esta query en tu ProductDAO
        return productDAO.findAll().toString().let { emptyList() }
    }

    suspend fun insertProduct(productData: ProductData) {
        productDAO.insertProduct(productData)
    }

    suspend fun updateProduct(productData: ProductData) {
        productDAO.updateProduct(productData)
    }

    suspend fun deleteProduct(productData: ProductData) {
        productDAO.deleteProduct(productData)
    }
}