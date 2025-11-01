package com.actividad_22.screen

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.UserViewModel

/**
 * Pantalla que muestra un resumen de todos los clientes registrados.
 *
 * Esta pantalla muestra una lista de todos los clientes de la base de datos.
 * También proporciona una barra inferior con opciones para limpiar la base de datos (funcionalidad futura)
 * y para navegar de vuelta a la pantalla de inicio.
 *
 * @param navController El controlador de navegación para manejar las transiciones entre pantallas.
 * @param userViewModel El ViewModel que proporciona los datos de los clientes.
 */
@Composable
fun SummaryScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    // Recolecta la lista de todos los clientes del ViewModel como un estado.
    val clientList by userViewModel.allClients.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Barra de aplicación inferior con acciones.
            BottomAppBar(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                containerColor = MaterialTheme.colorScheme.errorContainer
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón para limpiar la base de datos.
                    // TODO: En el futuro se hará la lógica para limpiar la base de datos.
                    Button(onClick = {
                        Log.d("SummaryScreen", "Base de datos limpiada")
                    }) {
                        Text("Limpiar DB")
                    }
                    // Botón de ícono para navegar a la pantalla de inicio.
                    IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Volver al Inicio"
                        )
                    }

                }
            }
        }
    ) { innerPadding ->
        // Contenido principal de la pantalla.
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Resumen de Clientes", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            // Comprueba si la lista de clientes no está vacía.
            if (clientList.isNotEmpty()) {
                // Muestra la lista de clientes en una LazyColumn.
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(clientList) { client ->
                        // Tarjeta para cada cliente.
                        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Muestra los detalles del cliente.
                                Text(text = "ID: ${client.id_client}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Nombre: ${client.name_client}")
                                Text(text = "Email: ${client.email_client}")
                                Text(text = "Dirección: ${client.direction_client}")
                            }
                        }
                    }
                }
            } else {
                // Muestra un mensaje si no hay clientes.
                Text("No hay clientes en la base de datos")
            }
        }
    }
}
