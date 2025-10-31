package com.actividad_22.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.actividad_22.R
import com.actividad_22.data.local.Product
import com.actividad_22.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _categoryProducts = MutableStateFlow<List<Product>>(emptyList())
    val categoryProducts: StateFlow<List<Product>> = _categoryProducts

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

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            repository.insertProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
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