package com.actividad_22.data.repository

import com.actividad_22.data.local.Product
import com.actividad_22.data.local.ProductDAO
import kotlinx.coroutines.flow.Flow

class ProductRepository(val productDAO: ProductDAO) {

    fun getAllProducts(): Flow<List<Product>> = productDAO.findAll()

    suspend fun getProductsByCategory(category: Int): List<Product> {
        // Necesitarás agregar esta query en tu ProductDAO
        return productDAO.findAll().toString().let { emptyList() }
    }

    suspend fun insertProduct(product: Product) {
        productDAO.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDAO.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDAO.deleteProduct(product)
    }
}