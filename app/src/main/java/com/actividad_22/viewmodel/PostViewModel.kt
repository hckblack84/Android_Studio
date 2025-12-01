package com.actividad_22.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.actividad_22.apiRest.model.ApiProduct
import com.actividad_22.apiRest.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class PostViewModel : ViewModel(){

    private val repository = PostRepository()

    val _postList = MutableStateFlow<List<ApiProduct>>(emptyList())

    open val postList: StateFlow<List<ApiProduct>> = _postList


    val _postCartProducts = MutableStateFlow<List<ApiProduct>>(emptyList())

    val postCartProducts: StateFlow<List<ApiProduct>> = _postCartProducts



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

    fun addProductToCart(apiProduct: ApiProduct){
        _postCartProducts.value += apiProduct
    }



}