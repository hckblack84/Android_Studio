package com.actividad_22.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.actividad_22.apiRest.model.Product
import com.actividad_22.apiRest.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel(){

    private val repository = PostRepository()

    private val _postList = MutableStateFlow<List<Product>>(emptyList())

    val postList: StateFlow<List<Product>> = _postList


    val _postCartProducts = MutableStateFlow<List<Product>>(emptyList())

    val postCartProducts: StateFlow<List<Product>> = _postCartProducts



    init {
        fetchPosts()
    }

    private fun fetchPosts(){
        viewModelScope.launch {
            try{
                _postList.value = repository.getPosts()
            }catch (e: Exception){
                println("Error: ${e.localizedMessage}")
            }
        }
    }

    fun addProductToCart(product: Product){
        _postCartProducts.value += product
    }



}