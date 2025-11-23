package com.actividad_22.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.R
import com.actividad_22.navigation.Screen
import com.actividad_22.screen.FloatingBottomBar
import com.actividad_22.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF101010)) {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text(
                        "Menú principal",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 12.dp))

                    DrawerItem("Perfil", Icons.Default.Info) { viewModel.navigateTo(Screen.Profile) }
                    DrawerItem("Tienda", Icons.Default.ShoppingCart) { viewModel.navigateTo(Screen.Store) }
                    DrawerItem("Nosotros", Icons.Default.Info) { viewModel.navigateTo(Screen.Us) }
                    DrawerItem("Eventos", Icons.Default.Event) { viewModel.navigateTo(Screen.Event) }

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = { scope.launch { drawerState.close() } }) {
                        Text("Cerrar menú", color = Color(0xFF2196F3))
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.fondo1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Level Up Gaming", color = Color.White, fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                            }
                        },
                        actions = {
                            TextButton(onClick = { viewModel.navigateTo(Screen.Start) }) {
                                Text("Cerrar Sesión", color = Color(0xFFE91E63))
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xAA000000))
                    )
                },
                containerColor = Color.Transparent
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "¡Bienvenido a Level Up Gaming!",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(32.dp))

                    // Fila para hacer el diseño más visual
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedCircleButton(
                            imageRes = R.drawable.img_juego,
                            label = "Nosotros"
                        ) { viewModel.navigateTo(Screen.Us) }

                        AnimatedCircleButton(
                            imageRes = R.drawable.img_accesorio,
                            label = "Tienda"
                        ) { viewModel.navigateTo(Screen.Store) }

                        AnimatedCircleButton(
                            imageRes = R.drawable.img_perifericos,
                            label = "Eventos"
                        ) { viewModel.navigateTo(Screen.Event) }
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
                )}
        }
    }
}

@Composable
fun DrawerItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(title, color = Color.White) },
        selected = false,
        icon = { Icon(icon, contentDescription = null, tint = Color.White) },
        onClick = onClick
    )
}

@Composable
fun AnimatedCircleButton(imageRes: Int, label: String, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.95f else 1f)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(Color(0x33000000))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        pressed = true
                        onClick()
                    }
                )
                .animateContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = label,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(label, color = Color.White, fontWeight = FontWeight.SemiBold)
    }



}





