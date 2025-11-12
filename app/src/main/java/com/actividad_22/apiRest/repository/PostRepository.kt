package com.actividad_22.apiRest.repository

import com.actividad_22.apiRest.model.Product
import com.actividad_22.apiRest.remote.RetrofitInstance

class PostRepository {

    suspend fun getPosts(): List<Product>{
        return RetrofitInstance.api.getPosts()
    }

}