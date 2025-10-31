package com.actividad_22.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel@Composable
fun SummaryScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val clientList by userViewModel.allClients.collectAsState(initial = emptyList())

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Resumen de Usuario", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            if (clientList.isNotEmpty()) {
                clientList.forEach { client ->
                    Text(text = "ID: ${client.id_client}")
                    Text(text = "Nombre: ${client.name_client}")
                    Text(text = "Email: ${client.email_client}")
                    Text(text = "Dirección: ${client.direction_client}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                Text("No hay usuarios en la base de datos")
            }
        }
    }
}
