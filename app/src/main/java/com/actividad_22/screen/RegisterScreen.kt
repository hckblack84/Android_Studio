package com.actividad_22.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
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

    // Definición de colores para la paleta oscura
    val deepDarkBackground = Color(0xFF0F1218)
    val cardSurface = Color(0xFF1A1F2E)
    val accentColor = Color(0xFFF38A1D)
    val secondaryText = Color(0xFF8B92A8)
    val outlineColor = Color(0xFF2A3142)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(deepDarkBackground)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // --- Encabezado con logo y efecto de resplandor ---
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(60.dp))
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(accentColor.copy(alpha = 0.3f), Color.Transparent),
                        radius = 120f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wube_software), // Logo adaptado para fondo oscuro
                contentDescription = "Logo de la app",
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Crear Cuenta",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rellena los datos para unirte",
            color = secondaryText,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        // --- Campos de entrada ---
        val textFieldColors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = cardSurface,
            unfocusedContainerColor = cardSurface,
            cursorColor = accentColor,
            focusedIndicatorColor = accentColor,
            unfocusedIndicatorColor = outlineColor,
            focusedLabelColor = secondaryText,
            unfocusedLabelColor = secondaryText,
            focusedLeadingIconColor = accentColor,
            unfocusedLeadingIconColor = secondaryText,
            errorIndicatorColor = Color.Red,
            errorLabelColor = Color.Red,
            errorLeadingIconColor = Color.Red
        )

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = userViewModel::onNombreChange,
            label = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            isError = estado.errores.nombre != null,
            supportingText = {
                AnimatedVisibility(visible = estado.errores.nombre != null) {
                    Text(estado.errores.nombre ?: "", color = Color.Red, fontSize = 12.sp)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true
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
                    Text(estado.errores.correo ?: "", color = Color.Red, fontSize = 12.sp)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true
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
                    Text(estado.errores.clave ?: "", color = Color.Red, fontSize = 12.sp)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true
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
                    Text(estado.errores.direccion ?: "", color = Color.Red, fontSize = 12.sp)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Checkbox de términos ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = userViewModel::onAceptaTerminosChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = accentColor,
                    uncheckedColor = secondaryText,
                    checkmarkColor = deepDarkBackground
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Acepto los términos y condiciones",
                color = secondaryText,
                fontSize = 14.sp
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
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = Color.White)
        ) {
            Text("Crear Cuenta", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Navegación secundaria ---
        TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = secondaryText, fontSize = 14.sp)) {
                        append("¿Ya tienes cuenta? ")
                    }
                    withStyle(style = SpanStyle(color = accentColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                        append("Inicia sesión")
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
