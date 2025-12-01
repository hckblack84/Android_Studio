package com.actividad_22

import com.actividad_22.apiRest.model.ApiProduct
import com.actividad_22.viewmodel.PostViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest : StringSpec({

    val fakeApiProducts = listOf(
        ApiProduct(
            1,
            "product",
            "none",
            "unknown",
            "www.example.com",
            100,
            "description",
            "www.imageExample.com",
            2
        )
    )

    val testViewModel = object : PostViewModel() {
        fun fetchPosts() {
            _postList.value = fakeApiProducts
        }
    }

    runTest {
        testViewModel.fetchPosts()
        testViewModel.postList.value shouldContainExactly fakeApiProducts
    }
}
)