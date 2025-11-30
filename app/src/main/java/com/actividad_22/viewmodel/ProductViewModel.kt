package com.actividad_22.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.actividad_22.R
import com.actividad_22.data.local.ProductData
import com.actividad_22.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
open class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductData>>(emptyList())
    val products: StateFlow<List<ProductData>> = _products

    private val _categoryProducts = MutableStateFlow<List<ProductData>>(emptyList())
    val categoryProducts: StateFlow<List<ProductData>> = _categoryProducts

    val allProducts = repository.getAllProducts()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.getAllProducts().collect { productList ->
                _products.value = productList
            }
        }
    }

    fun loadProductsByCategory(category: Int) {
        viewModelScope.launch {
            val products = repository.getProductsByCategory(category)
            _categoryProducts.value = products
        }
    }

    fun insertProduct(productData: ProductData) {
        viewModelScope.launch {
            repository.insertProduct(productData)
        }
    }

    fun updateProduct(productData: ProductData) {
        viewModelScope.launch {
            repository.updateProduct(productData)
        }
    }

    fun deleteProduct(productData: ProductData) {
        viewModelScope.launch {
            repository.deleteProduct(productData)
        }
    }

    fun deleteProductById(id:Long){
        viewModelScope.launch {
            repository.deleteProductById(id)
        }
    }

    fun deleteAllProducts(){
        viewModelScope.launch {
            repository.truncateProducts()
        }
    }

    // Obtener categorías únicas de los productos
    fun getCategories(): List<Categoria> {
        val uniqueCategories = _products.value
            .map { it.category_product }
            .distinct()
            .sorted()

        return uniqueCategories.map { categoryId ->
            Categoria(
                id = categoryId.toString(),
                nombre = getCategoryName(categoryId),
                imageRes = getCategoryImage(categoryId)
            )
        }
    }

    private fun getCategoryName(category: Int): String {
        return when(category) {
            1 -> "Accesorios"
            2 -> "Juegos"
            3 -> "Periféricos"
            else -> "Categoría $category"
        }
    }

    private fun getCategoryImage(category: Int): Int {
        return when(category) {
            1 -> R.drawable.img_accesorio
            2 -> R.drawable.img_juego
            3 -> R.drawable.img_perifericos
            else -> R.drawable.ic_launcher_foreground
        }
    }
}

data class Categoria(
    val id: String,
    val nombre: String,
    val imageRes: Int
)