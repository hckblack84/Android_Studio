package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.R
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xAA000000)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Menu",
                        modifier = Modifier.padding(all = 16.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0C49D5)
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Perfil",
                                color = Color.White
                            )
                        },
                        selected = false,
                        modifier = Modifier,
                        onClick = {
                            scope.launch { drawerState.close() }
                            viewModel.navigateTo(Screen.Profile)
                        }

                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Tienda", color = Color.White) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            viewModel.navigateTo(Screen.Store)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Nosotros", color = Color.White) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            viewModel.navigateTo(Screen.Us)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Eventos", color = Color.White) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            viewModel.navigateBack()
                        }
                    )

                    Column(
                        modifier = Modifier.fillMaxSize().padding(end = 16.dp, bottom = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = "❮❮",
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0C49D5)
                                )
                            },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.fondo1),
                contentDescription = "Fondo de pantalla",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    TopAppBar(

                        title = {
                            Text(
                                text = "合",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.absolutePadding(
                                    left = 125.dp
                                )

                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xAA000000), // Negro semitransparente
                            navigationIconContentColor = Color.White,
                            titleContentColor = Color.White
                        ),
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                },
                containerColor = Color.Transparent
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier
                            .background(Color(0xAA000000))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "\uD83C\uDFAE Bienvenido a Level Up Gaming \uD83D\uDD79\uFE0F ",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 8f
                                )
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(height = 24.dp))

                    Button(
                        onClick = { viewModel.navigateTo(Screen.Start) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1976D2), // Azul
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Cerrar Sesión",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(height = 24.dp))


                }
            }
        }
    }
}