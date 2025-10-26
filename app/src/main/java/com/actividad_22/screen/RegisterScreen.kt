package com.actividad_22.screen

import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.navigation.*
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.UserViewModel


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
){
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,  // ✅ CORREGIDO - Sin llaves
            label = { Text(text = "Nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()  // ✅ También cambié fillMaxSize() a fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,  // ✅ Este está correcto
            label = { Text(text = "Correo") },
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()  // ✅ Cambié a fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text(text = "Clave") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()  // ✅ Cambié a fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.direccion,
            onValueChange = viewModel::onDirreccionChange,  // Nota: "Dirreccion" tiene doble 'r'
            label = { Text(text = "Direccion") },
            isError = estado.errores.direccion != null,
            supportingText = {
                estado.errores.direccion?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()  // ✅ Cambié a fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = viewModel::onAceptaTerminosChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Acepto los terminos y condiciones")
        }

        Button(
            onClick = {
                if (viewModel.validarFormulario()){
                    navController.navigate(Screen.Profile.route)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Registrar")
        }

        Button(
            onClick =  { navController.navigate(Screen.Home.route) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Volver al inicio")}
    }
}