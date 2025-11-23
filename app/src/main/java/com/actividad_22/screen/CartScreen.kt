package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val productList by productViewModel.products.collectAsState()

    // Calcular total
    val totalPrice = productList.sumOf { it.price_product }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1218))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp) // Espacio para la barra flotante
        ) {
            // Header personalizado
            CartHeader(
                itemCount = productList.size,
                onClearCart = { productViewModel.deleteAllProducts() }
            )

            if (productList.isNotEmpty()) {
                // Lista de productos
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(productList) { product ->
                        ProductCartItem(
                            product = product,
                            onDelete = { productViewModel.deleteProductById(product.id_product) }
                        )
                    }

                    // Espaciador final
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Footer con total
                CartFooter(
                    totalPrice = totalPrice,
                    itemCount = productList.size
                )
            } else {
                // Estado vacío
                EmptyCartState()
            }
        }

        // Bottom Navigation Bar flotante con efecto glassmorphism
        FloatingBottomBar(
            onHomeClick = { viewModel.navigateTo(Screen.Home) },
            onEventClick = {viewModel.navigateTo(Screen.Event)},
            onCartClick = { viewModel.navigateTo(Screen.Cart) },
            onStoreClick = { viewModel.navigateTo(Screen.Store) },
            onProfileClick = { viewModel.navigateTo(Screen.Profile) },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CartHeader(
    itemCount: Int,
    onClearCart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1F2E),
                        Color(0xFF0F1218)
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Mi Carrito",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$itemCount ${if (itemCount == 1) "producto" else "productos"}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8B92A8)
                )
            }

            // Botón limpiar carrito
            if (itemCount > 0) {
                IconButton(
                    onClick = onClearCart,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Color(0xFFFF4444).copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Vaciar carrito",
                        tint = Color(0xFFFF4444),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCartItem(
    product: ProductData,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1F2E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del producto
            Box(
                modifier = Modifier
                    .size(116.dp)
                    .clip(RoundedCornerShape(16.dp))
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
                Image(
                    painter = painterResource(id = product.image_product),
                    contentDescription = product.name_product,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del producto
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = product.name_product,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Categoría: Gaming",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF8B92A8)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Precio
                    Text(
                        text = "$${String.format("%.2f", product.price_product)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF38A1D)
                    )

                    // Botón eliminar
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                Color(0xFFFF4444).copy(alpha = 0.2f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFFF4444),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartFooter(
    totalPrice: Double,
    itemCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F1218),
                        Color(0xFF1A1F2E)
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        // Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF2A3142))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Resumen
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Total",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8B92A8)
                )
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Botón de checkout
            Button(
                onClick = { /* Acción de checkout */ },
                modifier = Modifier
                    .height(56.dp)
                    .width(160.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF38A1D)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Checkout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun EmptyCartState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icono grande
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1F2E)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = null,
                tint = Color(0xFF8B92A8),
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tu carrito está vacío",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Agrega productos desde la tienda\npara verlos aquí",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF8B92A8),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para ir a la tienda (opcional)
        Button(
            onClick = { /* Navegar a tienda */ },
            modifier = Modifier
                .height(50.dp)
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF38A1D)
            ),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = "Ir a la tienda",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}