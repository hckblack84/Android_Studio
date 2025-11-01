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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sobre Nosotros") }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                            Icon(Icons.Default.Home, contentDescription = "Volver al inicio")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Añadido para evitar desbordamiento en pantallas pequeñas
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Somos una tienda dedicada a ofrecer productos de alta calida reiser ache pe " +
                            "y brindar una atencion a factorio fans hola.",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))


                Card(

                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.luchito),
                            contentDescription = "Luis",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
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
                Card {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
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
                // Fila de iconos de redes sociales clickeables
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los iconos uniformemente
                ) {
                    val context = LocalContext.current


                    @Composable
                    fun SocialButton(iconRes: Int, url: String, contentDesc: String) {
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }) {
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = contentDesc,
                                tint = Color.Unspecified // Evita que se aplique un tinte negro por defecto
                            )
                        }
                    }

                    SocialButton(R.drawable.instagram, "https://www.instagram.com", "Instagram")
                    SocialButton(R.drawable.meta, "https://www.facebook.com", "Facebook")
                    SocialButton(R.drawable.tiktok, "https://www.tiktok.com", "Tiktok")
                    SocialButton(R.drawable.factorio, "https://factorio.com", "Factorio")
                }
            }
        }
    }
}
