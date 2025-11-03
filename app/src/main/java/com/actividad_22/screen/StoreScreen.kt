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
import com.actividad_22.data.local.ProductData
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.ProductViewModel

/**
 * Clase de datos que representa un producto en la tienda.
 *
 * @property imageRes El ID del recurso de la imagen del producto.
 * @property name El nombre del producto.
 * @property price El precio del producto.
 */
data class Product(
    val imageRes: Int,
    val name: String,
    val price: Double
)

/**
 * Composable que define la pantalla principal de la tienda.
 * Muestra una lista de productos en una cuadrícula y una barra de navegación inferior.
 *
 * @param navController El controlador de navegación para manejar los desplazamientos entre pantallas.
 * @param viewModel El ViewModel principal que contiene la lógica de negocio y el estado de la UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(navController: NavHostController, viewModel: MainViewModel, productViewModel: ProductViewModel) {
    // Lista de productos que se mostrarán en la tienda.
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

    // Estructura principal de la pantalla usando Scaffold.
    Scaffold(
        topBar = {
            TopAppBar(
                // Título de la barra superior.
                title = { Text("🛒 Tienda de Accesorios") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                // Barra de navegación inferior con esquinas superiores redondeadas.
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
                        // Botón para navegar a la pantalla de inicio.
                        IconButton(onClick = { viewModel.navigateTo(Screen.Home) }) {
                            Icon(Icons.Filled.Home, contentDescription = "Inicio")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        // Botón para navegar a la pantalla de perfil.
                        IconButton(onClick = { viewModel.navigateTo(Screen.Profile) }) {
                            Icon(Icons.Filled.Person, contentDescription = "Perfil")
                        }
                    }
                    // Botón de acción flotante (FAB) para el carrito de compras.
                    FloatingActionButton(
                        onClick = { viewModel.navigateTo(Screen.Cart) },
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
        // Cuadrícula vertical perezosa para mostrar la lista de productos.
        // `LazyVerticalGrid` es eficiente para listas largas, ya que solo compone los elementos visibles.
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Itera sobre la lista de productos y crea un `ProductCardSimple` para cada uno.
            items(productList) { product ->
                ProductCardSimple(
                    imageRes = product.imageRes,
                    productName = product.name,
                    price = product.price,
                    addToCartEvent = {
                        //Agregar a la base de datos
                        productViewModel.insertProduct(ProductData(
                            name_product = product.name,
                            price_product = product.price,
                            description_product = "",
                            image_product = product.imageRes,
                            category_product = 1
                        ))
                        println("agregado a la base de datos")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
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
fun ProductCardSimple(
    imageRes: Int,
    productName: String,
    price: Double,
    addToCartEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Contenedor principal de la tarjeta con elevación y esquinas redondeadas.
    Card(
        modifier = modifier.heightIn(min = 280.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            // Columna para organizar los elementos verticalmente.
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen del producto.
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

            // Nombre del producto.
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

            // Precio del producto, formateado a dos decimales.
            Text(
                text = "$${String.format("%.2f", price)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para agregar el producto al carrito.
            Button(
                onClick = addToCartEvent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                ),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                // Ícono de "Agregar".
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                // Texto del botón.
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