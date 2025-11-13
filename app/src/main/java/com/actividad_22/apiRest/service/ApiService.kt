package com.actividad_22.apiRest.service

import com.actividad_22.apiRest.model.Post
import com.actividad_22.apiRest.model.Product
import retrofit2.http.GET

interface ApiService {

    @GET("product")
    suspend fun getPosts(): List<Product>;
}