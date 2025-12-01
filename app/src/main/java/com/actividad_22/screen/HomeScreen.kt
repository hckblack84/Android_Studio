package com.actividad_22.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import java.time.LocalTime
import kotlinx.coroutines.launch

private val DeepDarkBackground = Color(0xFF0F1218)
private val CardSurface = Color(0xFF1A1F2E)
private val AccentColor = Color(0xFFF38A1D)
private val SecondaryText = Color(0xFF8B92A8)
private val OutlineColor = Color(0xFF2A3142)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val greeting = remember {
        val currentTime = LocalTime.now()
        when {
            currentTime.isAfter(LocalTime.of(5, 0)) && currentTime.isBefore(LocalTime.of(12, 0)) -> "Buenos días"
            currentTime.isAfter(LocalTime.of(12, 0)) && currentTime.isBefore(LocalTime.of(18, 0)) -> "Buenas tardes"
            else -> "Buenas noches"
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = CardSurface,
                modifier = Modifier.width(280.dp)
            ) {
                DrawerContent(
                    onNavigate = { screen ->
                        viewModel.navigateTo(screen)
                        scope.launch { drawerState.close() }
                    },
                    onCloseDrawer = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepDarkBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp) // Espacio para la barra flotante
                    .verticalScroll(scrollState)
            ) {
                // Header con menú hamburguesa
                HomeHeader(
                    greeting = greeting,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onLogoutClick = { viewModel.navigateTo(Screen.Start) }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Título de bienvenida
                WelcomeBanner()

                Spacer(modifier = Modifier.height(40.dp))

                // Botones de navegación principales
                NavigationButtons(
                    onStoreClick = { viewModel.navigateTo(Screen.Store) },
                    onEventsClick = { viewModel.navigateTo(Screen.Event) },
                    onAboutClick = { viewModel.navigateTo(Screen.Profile) }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Sección de información rápida
                QuickInfoSection()

                Spacer(modifier = Modifier.height(32.dp))
            }
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
}

@Composable
fun HomeHeader(
    greeting: String,
    onMenuClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        CardSurface,
                        DeepDarkBackground
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Barra superior con menú y logout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón de menú hamburguesa
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(CardSurface)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Título de la app
            Text(
                text = "Level Up Gaming",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Botón de logout
            TextButton(
                onClick = onLogoutClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFFE91E63)
                )
            ) {
                Text(
                    text = "Salir",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Saludo personalizado
        Text(
            text = "$greeting! ",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Bienvenido de nuevo ",//incorporar el ususario que a inciado la sesion
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = SecondaryText
        )
    }
}

@Composable
fun WelcomeBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            CardSurface,
                            AccentColor.copy(alpha = 0.2f)
                        )
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎮",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "¡Bienvenido a Level Up Gaming!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu destino para productos gaming",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = SecondaryText,
                textAlign = TextAlign.Center
            )

        }

    }

}

@Composable
fun NavigationButtons(
    onStoreClick: () -> Unit,
    onEventsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Botón Tienda
        AnimatedNavButton(
            icon = Icons.Default.ShoppingCart,
            title = "Tienda",
            description = "Explora nuestros productos",
            iconEmoji = "🛒",
            onClick = onStoreClick
        )

        // Botón Eventos
        AnimatedNavButton(
            icon = Icons.Default.Event,
            title = "Eventos",
            description = "Descubre eventos próximos",
            iconEmoji = "🎪",
            onClick = onEventsClick
        )

        // Botón Perfil
        AnimatedNavButton(
            icon = Icons.Default.Person,
            title = "Perfil",
            description = "Tu informacion!",
            iconEmoji = "ℹ️",
            onClick = onAboutClick
        )
    }
}

@Composable
fun AnimatedNavButton(
    icon: ImageVector,
    title: String,
    description: String,
    iconEmoji: String,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.97f else 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                onClick = {
                    pressed = true
                    onClick()
                }
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono circular
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentColor.copy(alpha = 0.3f),
                                AccentColor.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconEmoji,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = SecondaryText
                )
            }

            // Flecha
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = AccentColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            kotlinx.coroutines.delay(100)
            pressed = false
        }
    }
}

@Composable
fun QuickInfoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Información",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Cards de información
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(
                emoji = "📦",
                title = "Productos",
                subtitle = "Premium",
                modifier = Modifier.weight(1f)
            )

            InfoCard(
                emoji = "⚡",
                title = "Envío",
                subtitle = "Rápido",
                modifier = Modifier.weight(1f)
            )

            InfoCard(
                emoji = "🎁",
                title = "Ofertas",
                subtitle = "Exclusivas",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun InfoCard(
    emoji: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = emoji,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = SecondaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DrawerContent(
    onNavigate: (Screen) -> Unit,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CardSurface)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Header del drawer
        Text(
            text = "Level Up Gaming",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Menú De Desarrollo",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = SecondaryText
        )

        Spacer(modifier = Modifier.height(32.dp))

        Divider(color = OutlineColor, thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Items del drawer
        DrawerMenuItem(
            icon = Icons.Default.Person,
            title = "Post",
            onClick = { onNavigate(Screen.Post) }
        )

        DrawerMenuItem(
            icon = Icons.Default.ShoppingCart,
            title = "Summary",
            onClick = { onNavigate(Screen.Summary) }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón cerrar
        TextButton(
            onClick = onCloseDrawer,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(
                contentColor = AccentColor
            )
        ) {
            Text(
                text = "Cerrar menú",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(AccentColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = AccentColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}