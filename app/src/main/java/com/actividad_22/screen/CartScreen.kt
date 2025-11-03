package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.data.local.ProductData
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.ProductViewModel
import com.actividad_22.viewmodel.UserViewModel

@Composable
fun CartScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    viewModel: MainViewModel = viewModel(),
    productViewModel: ProductViewModel
) {
    // Observa el Flow
    val productList by productViewModel.products.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {
                navController.navigate(Screen.Store.route)
            }) {
                Text("Back to store")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Si la lista no está vacía, la mostramos
            if (productList.isNotEmpty()) {
                LazyColumn {
                    items(productList) { product ->
                        ProductItem(product)
                    }
                }
            } else {
                Text("No hay productos en el carrito")
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = product.name_product,
            style = MaterialTheme.typography.titleMedium
        )

        Text(text = "Precio: ${product.price_product}")
        Text(text = "Categoría: ${product.category_product}")

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = product.image_product),
            contentDescription = product.name_product,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}
