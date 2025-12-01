package com.actividad_22.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.actividad_22.apiRest.model.ApiProduct
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(postViewModel: PostViewModel, mainViewModel: MainViewModel){

    val posts = postViewModel.postList.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de post") }
            )
            Button(onClick = {mainViewModel.navigateTo(Screen.PostCart)}) {
                Text(text = "Ir a carrito")
            }
        }
    ) { innerPadding ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding))
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0F1218)),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(posts) { product ->
                    PostProductCard(
                        apiProduct = product,
                        addToCartEvent = {
                            postViewModel.addProductToCart(apiProduct = product)
                            println("Producto agregado al carrito: ${product.nameProduct}")
                            println("Carrito actual: ${postViewModel.postCartProducts.value}")
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun PostProductCard(
    apiProduct: ApiProduct,
    addToCartEvent: () -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1F2E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen del producto - Hero style
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF2A3142),
                                Color(0xFF1A1F2E)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = apiProduct.urlImage,
                    contentDescription = apiProduct.nameProduct,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentScale = ContentScale.Fit


                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Indicador de puntos (simulado)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(2) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == 0) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == 0) Color(0xFFF38A1D)
                                else Color(0xFF4A5568)
                            )
                    )
                    if (index < 1) Spacer(modifier = Modifier.width(6.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categoría pequeña
            Text(
                text = apiProduct.categoryProduct,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8B92A8),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del producto
            Text(
                text = apiProduct.nameProduct,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Precio destacado
            Text(
                text = "$" + (apiProduct.priceProduct),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF38A1D)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            if (apiProduct.descriptionProduct.isNotEmpty()) {
                Text(
                    text = apiProduct.descriptionProduct,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFB0B7C3),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Control de cantidad
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(0xFF2A3142)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (quantity > 1) quantity-- },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease",
                        tint = Color.White
                    )
                }

                Text(
                    text = quantity.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                IconButton(
                    onClick = { quantity++ },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Add to Cart
            Button(
                onClick = {
                    repeat(quantity) { addToCartEvent() }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF38A1D)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Add to cart",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}