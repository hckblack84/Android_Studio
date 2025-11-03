package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.data.local.ProductData
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.ProductViewModel
import com.actividad_22.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    viewModel: MainViewModel = viewModel(),
    productViewModel: ProductViewModel
) {
    val productList by productViewModel.products.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Carrito de compras") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { navController.navigate(Screen.Store.route) }) {
                        Text("Volver a tienda")
                    }

                    Button(
                        onClick = { productViewModel.deleteAllProducts() },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text("Vaciar carrito")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (productList.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(productList) { product ->
                            ProductItem(product, productViewModel)
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay productos en el carrito",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ProductItem(product: ProductData, productViewModel: ProductViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = product.image_product),
                contentDescription = product.name_product,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.name_product,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = "Precio: ${product.price_product}")
            Text(text = "Categoría: ${product.category_product}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { productViewModel.deleteProductById(product.id_product) },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Eliminar")
            }
        }
    }
}
