package com.actividad_22.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.actividad_22.viewmodel.UserViewModel

@Composable
fun SummaryScreen(viewModel: UserViewModel){
    val estado by viewModel.estado.collectAsState()

    Column(Modifier.padding(16.dp)){
        Text("Resumen de informacion : ", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Nombre: ${estado.nombre}")
        Text(text = "Correo: ${estado.correo}")
        Text(text = "Clave: ${estado.clave}")
        Text(text = "Direccion: ${estado.direccion}")
        Text(text = "Acepta terminos: ${estado.aceptaTerminos}")

    }
}