package com.actividad_22.data.repository

import com.actividad_22.data.local.Product
import com.actividad_22.data.local.ProductDAO
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDAO: ProductDAO) {
    val allProducts: Flow<List<Product>> = productDAO.findAll()

    suspend fun insertProduct(product: Product) {
        productDAO.insertProduct(product)
    }

suspend fun  deleteProduct(product: Product){
    productDAO.deleteProduct(product)
}

}