package com.actividad_22.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.data.local.Client
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.UserViewModel
import kotlinx.coroutines.flow.toList


@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
){
    val estado by userViewModel.estado.collectAsState()
    //val clientList by userViewModel.allClients.collectAsState(initial = emptyList())


    Surface (modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Registro de usuario")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = userViewModel::onNombreChange,
            label = { Text(text = "Nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.correo,
            onValueChange = userViewModel::onCorreoChange,
            label = { Text(text = "Correo") },
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.clave,
            onValueChange = userViewModel::onClaveChange,
            label = { Text(text = "Clave") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.direccion,
            onValueChange = userViewModel::onDirreccionChange,
            label = { Text(text = "Direccion") },
            isError = estado.errores.direccion != null,
            supportingText = {
                estado.errores.direccion?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = userViewModel::onAceptaTerminosChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Acepto los terminos y condiciones")
        }
        TextButton(onClick = {navController.navigate(Screen.Login.route)}) {
            Text(text = "¿Ya tienes cuenta? Iniciar sesión")
        }
        Button(
            onClick = {
                if (userViewModel.validarFormulario()){
                    println("asdjasdlsadkljasdkljsadlasjdkljsa")
                    userViewModel.insertClient(Client(name_client = estado.nombre,
                                                    email_client = estado.correo,
                                                    password_client = estado.clave,
                                                    direction_client = estado.direccion))

                    /*for (client:Client in userViewModel.allClients as List<Client>){
                        println("===============================")
                        println(client.name_client)
                        println(client.email_client)
                        println(client.password_client)
                        println(client.direction_client)
                        println("===============================")
                    }*/

                    //navController.navigate(Screen.Login.route)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Registrar")
        }

        /*LazyColumn {
            items(clientList) { client ->
                Text(text = "id: ${client.id_client}")
                Text(text = "name: ${client.name_client}")
                Text(text = "email: ${client.email_client}")
                Text(text = "password: ${client.password_client}")
                Text(text = "direction: ${client.direction_client}")
            }
        }*/

        }
    }
