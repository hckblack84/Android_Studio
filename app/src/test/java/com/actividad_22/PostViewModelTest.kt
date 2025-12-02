package com.actividad_22

import com.actividad_22.apiRest.model.ApiProduct
import com.actividad_22.viewmodel.PostViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

/**
 * Esta anotación indica que estamos usando funciones experimentales de corutinas,
 * que son una forma de manejar tareas en segundo plano.
 */
@OptIn(ExperimentalCoroutinesApi::class)
// Esta es una clase de prueba para el PostViewModel. Su objetivo es asegurarse de que el ViewModel funciona correctamente.
class PostViewModelTest : StringSpec({

    // Creamos una lista falsa de productos para usarla en nuestras pruebas.
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

    // Creamos una versión de prueba de nuestro PostViewModel.
    val testViewModel = object : PostViewModel() {
        // Sobrescribimos la función `fetchPosts` para que, en lugar de llamar a la API real,
        // simplemente asigne nuestra lista de productos falsos a la lista de posts.
        fun fetchPosts() {
            _postList.value = fakeApiProducts
        }
    }

    // Aquí comienza la prueba. 'runTest' nos permite probar código que usa corutinas.
    runTest {
        // 1. Llamamos a nuestra función de prueba `fetchPosts` para que cargue los datos falsos.
        testViewModel.fetchPosts()
        // 2. Comprobamos que la lista de posts en el ViewModel contiene exactamente los productos falsos que definimos.
        testViewModel.postList.value shouldContainExactly fakeApiProducts
    }
}
)