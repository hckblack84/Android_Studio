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

/**
 * Define la pantalla de registro de la aplicación.
 * Esta función se encarga de mostrar todos los elementos visuales de la pantalla de registro,
 * como campos de texto para el nombre, correo, contraseña y dirección,
 * así como un botón para crear la cuenta y un enlace para ir a la pantalla de inicio de sesión.
 *
 * @param navController Controlador para gestionar la navegación entre pantallas.
 * @param userViewModel Modelo que gestiona los datos y la lógica del formulario de registro.
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
) {
    // Obtiene el estado actual del formulario (los datos que el usuario ha introducido).
    val estado by userViewModel.estado.collectAsState()

    // Define la paleta de colores para el tema oscuro de la pantalla.
    val deepDarkBackground = Color(0xFF0F1218)
    val cardSurface = Color(0xFF1A1F2E)
    val accentColor = Color(0xFFF38A1D)
    val secondaryText = Color(0xFF8B92A8)
    val outlineColor = Color(0xFF2A3142)

    // Crea una columna que ocupa toda la pantalla y centra su contenido.
    // Le da un color de fondo oscuro y permite desplazarse si el contenido es muy largo.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(deepDarkBackground)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente.
        verticalArrangement = Arrangement.Center // Centra los elementos verticalmente.
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Muestra el logo de la aplicación dentro de un círculo con un efecto de resplandor.
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
                painter = painterResource(id = R.drawable.wube_software),
                contentDescription = "Logo de la app",
                modifier = Modifier.size(70.dp)
            )
        }

        // Muestra los textos de bienvenida "Crear Cuenta" y "Rellena los datos para unirte".
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

        // Define el estilo visual para los campos de texto (colores para cuando está enfocado, error, etc.).
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

        // Campo de texto para que el usuario ingrese su nombre completo.
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = userViewModel::onNombreChange,
            label = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            isError = estado.errores.nombre != null,
            supportingText = { // Muestra un mensaje de error si el nombre no es válido.
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

        // Campo de texto para que el usuario ingrese su correo electrónico.
        OutlinedTextField(
            value = estado.correo,
            onValueChange = userViewModel::onCorreoChange,
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = estado.errores.correo != null,
            supportingText = { // Muestra un mensaje de error si el correo no es válido.
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

        // Campo de texto para que el usuario ingrese su contraseña (los caracteres se ocultan).
        OutlinedTextField(
            value = estado.clave,
            onValueChange = userViewModel::onClaveChange,
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(), // Oculta el texto de la contraseña.
            isError = estado.errores.clave != null,
            supportingText = { // Muestra un mensaje de error si la contraseña no es válida.
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

        // Campo de texto para que el usuario ingrese su dirección.
        OutlinedTextField(
            value = estado.direccion,
            onValueChange = userViewModel::onDirreccionChange,
            label = { Text("Dirección") },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
            isError = estado.errores.direccion != null,
            supportingText = { // Muestra un mensaje de error si la dirección no es válida.
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

        // Muestra una casilla de verificación para aceptar los términos y condiciones.
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

        // Botón principal para crear la cuenta.
        Button(
            onClick = {
                if (userViewModel.validarFormulario()) {
                    // Si los datos son válidos, crea un nuevo cliente.
                    userViewModel.insertClient(
                        Client(
                            name_client = estado.nombre,
                            email_client = estado.correo,
                            password_client = estado.clave,
                            direction_client = estado.direccion
                        )
                    )
                    // Navega a la pantalla de inicio de sesión y elimina la de registro del historial.
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

        // Texto con un botón para que el usuario navegue a la pantalla de inicio de sesión si ya tiene una cuenta.
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
