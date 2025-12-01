package com.actividad_22

import com.actividad_22.apiRest.model.ApiProduct
import com.actividad_22.apiRest.repository.PostRepository
import com.actividad_22.apiRest.service.ApiService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class TestablePostRepository(private val testApi: ApiService) : PostRepository() {

    override suspend fun getPosts(): List<ApiProduct> {
        return testApi.getPosts()
    }
}

class PostRepositoryTest : StringSpec({

    val fakeApiProducts = listOf(
        ApiProduct(
            1,
            "name",
            "category",
            "distributor",
            "linkDistributor",
            100,
            "description",
            "urlImage",
            2))

    val mockApi = mockk<ApiService>()
    coEvery { mockApi.getPosts() } returns fakeApiProducts

    val repository = TestablePostRepository(mockApi)

    runTest {
        val result = repository.getPosts()
        result shouldContainExactly fakeApiProducts
    }

}
)