package com.actividad_22.apiRest.repository

import com.actividad_22.apiRest.model.ApiProduct
import com.actividad_22.apiRest.remote.RetrofitInstance

open class PostRepository {

    open suspend fun getPosts(): List<ApiProduct> {
        return RetrofitInstance.api.getPosts()
    }

}