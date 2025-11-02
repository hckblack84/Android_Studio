package com.actividad_22.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.R
import com.actividad_22.data.local.Client
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.UserViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
) {
    val estado by userViewModel.estado.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- Encabezado con logo o ícono ---
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.wube_software), // Puedes cambiar el ícono
                contentDescription = "Logo registro",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            // --- Campos de entrada ---
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = userViewModel::onNombreChange,
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = estado.errores.nombre != null,
                supportingText = {
                    AnimatedVisibility(visible = estado.errores.nombre != null) {
                        Text(estado.errores.nombre ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = estado.correo,
                onValueChange = userViewModel::onCorreoChange,
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                isError = estado.errores.correo != null,
                supportingText = {
                    AnimatedVisibility(visible = estado.errores.correo != null) {
                        Text(estado.errores.correo ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = estado.clave,
                onValueChange = userViewModel::onClaveChange,
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.clave != null,
                supportingText = {
                    AnimatedVisibility(visible = estado.errores.clave != null) {
                        Text(estado.errores.clave ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = estado.direccion,
                onValueChange = userViewModel::onDirreccionChange,
                label = { Text("Dirección") },
                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                isError = estado.errores.direccion != null,
                supportingText = {
                    AnimatedVisibility(visible = estado.errores.direccion != null) {
                        Text(estado.errores.direccion ?: "", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Checkbox de términos ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = userViewModel::onAceptaTerminosChange
                )
                Text(
                    text = "Acepto los términos y condiciones",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Botón principal ---
            Button(
                onClick = {
                    if (userViewModel.validarFormulario()) {
                        userViewModel.insertClient(
                            Client(
                                name_client = estado.nombre,
                                email_client = estado.correo,
                                password_client = estado.clave,
                                direction_client = estado.direccion
                            )
                        )
                        navController.navigate(Screen.Login.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("Registrar", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Navegación secundaria ---
            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {userViewModel.truncateClients()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Funny button")
            }
        }
    }
}
