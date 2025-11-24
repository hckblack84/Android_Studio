package com.actividad_22.screen

import android.icu.text.CaseMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.actividad_22.R
import com.actividad_22.data.local.ProductData
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.ProductViewModel

data class Product(
    val imageRes: Int,
    val name: String,
    val price: Double,
    val description: String = ""
)

@Composable
fun StoreScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    productViewModel: ProductViewModel
) {
    val productList = listOf(
        Product(
            R.drawable.mouse_pad,
            "Mouse Pad RGB",
            15.99,
            "Enhance your gaming setup with this vibrant RGB mouse pad."
        ),
        Product(
            R.drawable.teclado_gamer,
            "Teclado Mecánico",
            89.99,
            "Mechanical gaming keyboard with customizable RGB lighting."
        ),
        Product(
            R.drawable.audifonos_gamer,
            "Audífonos Gamer",
            45.99,
            "Immersive 7.1 surround sound gaming headset."
        ),
        Product(
            R.drawable.camara_gamer,
            "Webcam HD",
            59.99,
            "Crystal clear 1080p webcam for streaming and video calls."
        ),
        Product(
            R.drawable.microfono_gamer,
            "Micrófono USB",
            79.99,
            "Professional USB microphone with studio-quality sound."
        ),
        Product(
            R.drawable.monitor_gamer,
            "Monitor 24''",
            199.99,
            "144Hz gaming monitor with vibrant colors and low response time."
        ),
        Product(
            R.drawable.silla_gamer,
            "Silla Gamer",
            299.99,
            "Ergonomic gaming chair designed for long gaming sessions."
        ),
        Product(
            R.drawable.luquitas,
            "Escritorio",
            149.99,
            "Spacious gaming desk with cable management system."
        ),
        Product(
            R.drawable.lampara_led,
            "Lámpara LED",
            29.99,
            "RGB LED lamp to complete your gaming atmosphere."
        ),
    )
Row() {
    Text(text = "HOla" +
            "")
}
    Box(modifier = Modifier.fillMaxSize()) {


        // Contenido principal - Lista de productos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F1218)),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(productList) { product ->
                ProductCard(
                    product = product,
                    addToCartEvent = {
                        productViewModel.insertProduct(
                            ProductData(
                                name_product = product.name,
                                price_product = product.price,
                                description_product = product.description,
                                image_product = product.imageRes,
                                category_product = 1
                            )
                        )
                    }
                )
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
/**
 * Composable que representa la tarjeta de un producto individual.
 *
 * @param imageRes El ID del recurso de la imagen del producto.
 * @param productName El nombre del producto.
 * @param price El precio del producto.
 * @param addToCartEvent La acción a ejecutar cuando se hace clic en el botón "Agregar".
 * @param modifier El modificador para personalizar el estilo y el diseño de la tarjeta.
 */

@Composable
fun ProductCard(
    product: Product,
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
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
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
                text = "GAMING",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8B92A8),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del producto
            Text(
                text = product.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Precio destacado
            Text(
                text = "$${String.format("%.2f", product.price)}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF38A1D)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            if (product.description.isNotEmpty()) {
                Text(
                    text = product.description,
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

@Composable
fun FloatingBottomBar(
    onEventClick: () -> Unit,
    onStoreClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        // Fondo con efecto glassmorphism
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(36.dp))
                .background(
                    Color(0xFFF38A1D).copy(alpha = 0.15f)
                )
                .blur(20.dp)
        )

        // Contenido de la barra
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(36.dp))
                .background(
                    Color(0xFFF38A1D).copy(alpha = 0.25f)
                )
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            //Eventos
            IconButton(
                onClick = onEventClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.EventBusy,
                    contentDescription = "Event",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Cart FAB
            FloatingActionButton(
                onClick = onCartClick,
                modifier = Modifier.size(64.dp),
                containerColor = Color(0xFFF38A1D),
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Cart",
                    modifier = Modifier.size(32.dp)
                )
            }
            // Store
            IconButton (
                onClick = onStoreClick,
                modifier = Modifier.size(64.dp),
                )
             {
                Icon(
                    imageVector = Icons.Filled.Store,
                    contentDescription = "Store",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )

            }



            // Profile
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(

                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}