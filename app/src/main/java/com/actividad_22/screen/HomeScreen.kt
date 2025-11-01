package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.actividad_22.ui.theme.Pink40
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
                            viewModel.navigateTo(Screen.Event)
                        }
                    )
                    NavigationDrawerItem(
                        label = {Text("Base de datos", color = Pink40)},
                        selected = false,
                        onClick = {scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Summary)}

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

            Image(
                painter = painterResource(id = R.drawable.fondo1),
                contentDescription = "Fondo de pantalla",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    TopAppBar (
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "",
                                    style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                )
                            }
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
                        },
                        actions = {
                            TextButton(onClick = { viewModel.navigateTo(Screen.Start) }) {
                                Text(
                                    text = "Cerrar Sesión",
                                    color = Color(0xFFE91E63),
                                    fontWeight = FontWeight.Bold
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
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                        Text(text = "¡Bienvenido a level up gaming!", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { viewModel.navigateTo(Screen.Us) },
                        shape = CircleShape,
                        modifier = Modifier.size(120.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_juego),
                            contentDescription = "Nosotros",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text("Nosotros", color = Color.White, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.navigateTo(Screen.Store) },
                        shape = CircleShape,
                        modifier = Modifier.size(120.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_accesorio),
                            contentDescription = "Tienda",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text("Tienda", color = Color.White, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.navigateTo(Screen.Event) },
                        shape = CircleShape,
                        modifier = Modifier.size(120.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_perifericos),
                            contentDescription = "Eventos",
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            contentScale = ContentScale.Crop)
                    }
                    Text("Eventos", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}