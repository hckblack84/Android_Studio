package com.actividad_22.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.actividad_22.R
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel

// ===================================================================
// COLORES ESTANDARIZADOS (AESTHETIC)
// ===================================================================
private val DeepDarkBackground = Color(0xFF0F1218)
private val CardSurface = Color(0xFF1A1F2E)
private val AccentColor = Color(0xFFF38A1D)
private val SecondaryText = Color(0xFF8B92A8)
private val OutlineColor = Color(0xFF2A3142)

// ===================================================================
// FUNCIÓN AUXILIAR DE BOTÓN SOCIAL
// ===================================================================
@Composable
private fun SocialButton(iconRes: Int, url: String, contentDesc: String) {
    val context = LocalContext.current
    IconButton(onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }) {
        // Envolver los iconos en un Box para mantener el estilo de fondo
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CardSurface)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = contentDesc,
                tint = Color.Unspecified // Mantiene el color original del icono
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("¡Pagina Informativa!", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = CardSurface
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(DeepDarkBackground) // Aplicar fondo oscuro
            .padding(paddingValues)
        ) {
            // Contenido con scroll
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título principal - usa el color de acento
                Text(
                    text = "¡Eventos Próximos!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AccentColor
                )
                Text(
                    text = "Marquitos House",
                    style = MaterialTheme.typography.titleMedium,
                    color = SecondaryText
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Descripción principal - usa el color de superficie oscuro y borde sutil
                Text(
                    text = "¡Bienvenidos a la inauguración de nuestra tienda! Nacimos de la pasión de dos amigos por el universo gaming y la tecnología. Nuestro objetivo es crear un espacio para la comunidad, donde puedan encontrar los mejores productos y compartir su entusiasmo.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardSurface.copy(alpha = 0.6f))
                        .border(
                            width = 1.dp,
                            color = OutlineColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                )


                Spacer(modifier = Modifier.height(24.dp))

                // Video informativo - usa el color de acento
                Text(
                    text = "¡Presiona aquí para ver el video informativo!",
                    color = AccentColor,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=J8SBp4SyvLc"))
                        context.startActivity(intent)
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Sección de próximos juegos - usa el color de acento
                Text(
                    text = "¡Próximos juegos!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = AccentColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Texto de recompensa
                Text(
                    text = "Como parte de nuestro lanzamiento, estamos emocionados de presentar un evento especial centrado en uno de los juegos que inspiró nuestra tienda. ¡Prepárense para la automatización y la expansión!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardSurface.copy(alpha = 0.6f))
                        .border(
                            width = 1.dp,
                            color = OutlineColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Imágenes de juegos con fondo y forma
                Image(
                    painter = painterResource(id = R.drawable.factoriologo),
                    contentDescription = "Factorio Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardSurface.copy(alpha = 0.8f)) // Fondo para el logo
                        .padding(10.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Y por supuesto!",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = SecondaryText
                )

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.factoriospaceage),
                    contentDescription = "Factorio Space Age",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardSurface.copy(alpha = 0.8f)) // Fondo para la imagen
                        .padding(10.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(80.dp))
                // Texto de recompensa
                Text(
                    text = "¡Nuestros equipo fundador!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = AccentColor
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ellos son los visionarios detrás de este proyecto. Su dedicación" +
                            " y amor por los videojuegos son el motor que impulsa nuestra tienda." +
                            "Conoce a los creadores que hicieron esto posible.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardSurface.copy(alpha = 0.6f))
                        .border(
                            width = 1.dp,
                            color = OutlineColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Cards de los miembros del equipo
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    FounderCard(imageRes = R.drawable.luchito, role = "Luis Paredes \nDesarrollador Frontend")
                    FounderCard(imageRes = R.drawable.luquitas, role = "Lucas Olmedo \nDesarrollador Backend")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sección de redes sociales
                Text(
                    "Conecta con nosotros en nuestras redes sociales",
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SocialButton(R.drawable.instagram, "https://www.instagram.com", "Instagram")
                    SocialButton(R.drawable.meta, "https://www.facebook.com", "Facebook")
                    SocialButton(R.drawable.tiktok, "https://www.tiktok.com", "Tiktok")
                    SocialButton(R.drawable.factorio, "https://factorio.com", "Factorio")
                }

                // Spacer para evitar solapamiento con la BottomBar
                Spacer(modifier = Modifier.height(80.dp))
            }

            // FloatingBottomBar se mantiene
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
private fun FounderCard(imageRes: Int, role: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = role,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, AccentColor, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = role,
            color = SecondaryText,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
