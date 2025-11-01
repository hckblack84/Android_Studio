package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button // Se mantiene para el botón "Volver"
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
// Importaciones para abrir URLs y corregir el color de los iconos
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.actividad_22.R

import androidx.navigation.NavController
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel


/**
 * Anotación para indicar el uso de APIs experimentales de Material 3.
 */
@OptIn(ExperimentalMaterial3Api::class)
/**
 * `UsScreen` es un Composable que representa la pantalla "Sobre Nosotros" de la aplicación.
 * Muestra información sobre el equipo, la misión de la tienda y enlaces a redes sociales.
 *
 * @param navController El controlador de navegación para manejar las transiciones entre pantallas.
 *                      Se utiliza para volver a la pantalla de inicio (`Screen.Home`).
 * @param viewModel El [MainViewModel] que podría usarse para manejar la lógica de negocio y el estado
 *                  de la interfaz de usuario, aunque en esta pantalla no se utiliza activamente.
 */
@Composable
fun UsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    // Scaffold proporciona la estructura básica de la pantalla (AppBar, contenido, etc.).
    Scaffold(
        // Barra superior de la aplicación.
        topBar = {
            TopAppBar(
                title = { Text("Sobre Nosotros") }
            )
        },
        // Barra inferior de la aplicación.
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                actions = {
                    // Fila que organiza los elementos de la barra inferior.
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        // Alinea el contenido al final (derecha).
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Botón de icono para navegar a la pantalla de inicio.
                        IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                            Icon(Icons.Default.Home, contentDescription = "Volver al inicio")
                        }
                    }
                }
            )
        }
        // El contenido principal de la pantalla se coloca dentro de este lambda.
        // `paddingValues` contiene los paddings necesarios para que el contenido no se superponga con las barras.
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                // Aplica el padding proporcionado por el Scaffold.
                .padding(paddingValues)
        ) {
            // Columna principal que organiza el contenido verticalmente.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // Permite el desplazamiento vertical si el contenido excede la altura de la pantalla.
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                // Centra los elementos horizontalmente.
                horizontalAlignment = Alignment.CenterHorizontally,
                // Alinea los elementos en la parte superior.
                verticalArrangement = Arrangement.Top
            ) {
                // Espaciador vertical.
                Spacer(modifier = Modifier.height(16.dp))

                // Texto de introducción sobre la tienda.
                Text(
                    text = "Somos una tienda dedicada a ofrecer productos de alta calida reiser ache pe " +
                            "y brindar una atencion a factorio fans hola.",
                    textAlign = TextAlign.Center
                )

                // Espaciador más grande.
                Spacer(modifier = Modifier.height(24.dp))

                // Tarjeta de presentación para el primer miembro del equipo.
                Card(

                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Imagen del miembro del equipo.
                        Image(
                            painter = painterResource(id = R.drawable.luchito),
                            contentDescription = "Luis",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            // Escala la imagen para que se ajuste y recorte si es necesario.
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("Luis Paredes", fontWeight = FontWeight.Bold)
                            Text("Encargado de la fabrica", textAlign = TextAlign.Center)
                            Text("Apasionado por armar el main bus en factorio.")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Tarjeta de presentación para el segundo miembro del equipo.
                Card {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            // Imagen del miembro del equipo.
                            painter = painterResource(id = R.drawable.luquitas),
                            contentDescription = "Lucas",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("Lucas olmedo", fontWeight = FontWeight.Bold)
                            Text("Exterminador profesional", textAlign = TextAlign.Center)
                            Text(
                                "Responsable de la gestión y la visión estratégica de expansion de la fabrica" +
                                        " 'Mata bichos nomas'.",
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Conecta con nosotros en nuestras redes sociales")
                Spacer(modifier = Modifier.height(8.dp))

                // Fila para los iconos de redes sociales.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    // Distribuye los iconos uniformemente a lo largo de la fila.
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // `LocalContext.current` obtiene el contexto de la actividad actual,
                    // necesario para crear un Intent que abra URLs.
                    val context = LocalContext.current

                    /**
                     * `SocialButton` es un Composable reutilizable para crear un botón de icono
                     * que abre una URL en el navegador web del dispositivo.
                     *
                     * @param iconRes El ID del recurso drawable para el icono.
                     * @param url La URL de la red social a abrir.
                     * @param contentDesc Descripción de contenido para accesibilidad.
                     */
                    @Composable
                    fun SocialButton(iconRes: Int, url: String, contentDesc: String) {
                        // Botón de icono que responde al clic.
                        IconButton(onClick = {
                            // Crea un Intent para ver una URL.
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            // Inicia la actividad (navegador) para manejar el Intent.
                            context.startActivity(intent)
                        }) {
                            // Muestra el icono de la red social.
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = contentDesc,
                                // `Color.Unspecified` asegura que se use el color original del icono
                                // en lugar de aplicar un tinte del tema actual (que suele ser negro o blanco).
                                tint = Color.Unspecified
                            )
                        }
                    }

                    // Se crean los botones para cada red social llamando al Composable `SocialButton`.
                    SocialButton(R.drawable.instagram, "https://www.instagram.com", "Instagram")
                    SocialButton(R.drawable.meta, "https://www.facebook.com", "Facebook")
                    SocialButton(R.drawable.tiktok, "https://www.tiktok.com", "Tiktok")
                    SocialButton(R.drawable.factorio, "https://factorio.com", "Factorio")
                }
            }
        }
    }
}
