package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.actividad_22.R
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel

data class Product(
    val imageRes: Int,
    val name: String,
    val price: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(navController: NavHostController, viewModel: MainViewModel) {
    val productList = listOf(
        Product(R.drawable.mouse_pad, "Mouse Pad RGB", 15.99),
        Product(R.drawable.teclado_gamer, "Teclado Mecánico", 89.99),
        Product(R.drawable.audifonos_gamer, "Audífonos Gamer", 45.99),
        Product(R.drawable.camara_gamer, "Webcam HD", 59.99),
        Product(R.drawable.microfono_gamer, "Micrófono USB", 79.99),
        Product(R.drawable.monitor_gamer, "Monitor 24''", 199.99),
        Product(R.drawable.silla_gamer, "Silla Gamer", 299.99),
        Product(R.drawable.luquitas, "Escritorio", 149.99),
        Product(R.drawable.lampara_led, "Lámpara LED", 29.99),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🛒 Tienda de Accesorios") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.navigateTo(Screen.Home) }) {
                            Icon(Icons.Filled.Home, contentDescription = "Inicio")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.navigateTo(Screen.Profile) }) {
                            Icon(Icons.Filled.Person, contentDescription = "Perfil")
                        }
                    }
                    FloatingActionButton(
                        onClick = { },
                        modifier = Modifier.offset(y = (-16).dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Ver Carrito",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productList) { product ->
                ProductCardSimple(
                    imageRes = product.imageRes,
                    productName = product.name,
                    price = product.price,
                    onAddClick = { println("${product.name} agregado al carrito") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ProductCardSimple(
    imageRes: Int,
    productName: String,
    price: Double,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.heightIn(min = 280.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = productName,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = productName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${String.format("%.2f", price)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAddClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                ),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Agregar",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}