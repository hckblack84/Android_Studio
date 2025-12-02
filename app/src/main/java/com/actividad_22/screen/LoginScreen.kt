package com.actividad_22.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.R
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.SessionManager
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel
import kotlinx.coroutines.launch

// Definición de la paleta de colores personalizada para la pantalla.
// Esto ayuda a mantener un estilo consistente y facilita los cambios de diseño.
// Son variables "privadas", lo que significa que solo se pueden usar dentro de este archivo.
private val DeepDarkBackground = Color(0xFF0F1218)
private val CardSurface = Color(0xFF1A1F2E)
private val AccentColor = Color(0xFFF38A1D)
private val SecondaryText = Color(0xFF8B92A8)
private val OutlineColor = Color(0xFF2A3142)

/**
 * Define la pantalla de inicio de sesión de la aplicación.
 *
 * @param navController El controlador que permite navegar entre pantallas.
 * @param viewModel El modelo de vista principal para la navegación.
 * @param userViewModel El modelo de vista que gestiona la lógica de usuario (como el login).
 */
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    // `sessionManager` se encarga de guardar y recuperar los datos del usuario que ha iniciado sesión.
    val sessionManager = remember { SessionManager(context) }
    // `coroutine` permite ejecutar tareas largas (como llamar a una API) sin bloquear la interfaz.
    val coroutine = rememberCoroutineScope()

    // `rememberSaveable` guarda el estado (el texto del email, contraseña, etc.)
    // incluso si la pantalla se destruye y se vuelve a crear (por ejemplo, al girar el dispositivo).
    // `email` guarda lo que el usuario escribe en el campo de correo.
    var email by rememberSaveable { mutableStateOf("") }
    // `password` guarda lo que el usuario escribe en el campo de contraseña.
    var password by rememberSaveable { mutableStateOf("") }
    // `showPassword` controla si la contraseña se muestra como texto o como puntos.
    var showPassword by rememberSaveable { mutableStateOf(false) }
    // `isLoading` se usa para mostrar una animación de carga mientras se procesa el inicio de sesión.
    var isLoading by remember { mutableStateOf(false) }

    // `Box` es un contenedor que apila sus elementos uno encima del otro.
    Box(
        modifier = Modifier
            // Ocupa todo el espacio disponible.
            .fillMaxSize()
            .background(DeepDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                // Permite desplazar la columna si el contenido no cabe en la pantalla.
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // `Spacer` añade un espacio vertical para separar elementos.
            Spacer(modifier = Modifier.height(60.dp))

            // Contenedor para el logo con un efecto de resplandor.
            Box(
                // Define el tamaño y el estilo del fondo.
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentColor.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = RoundedCornerShape(60.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Muestra la imagen del logo.
                Image(
                    painter = painterResource(id = R.drawable.factorio),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Texto principal de bienvenida.
            Text(
                text = "Bienvenido",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo o texto de instrucción.
            Text(
                text = "Inicia sesión para continuar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = SecondaryText
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Campo de texto para que el usuario introduzca su correo electrónico.
            OutlinedTextField(
                value = email,
                // `onValueChange` actualiza la variable `email` cada vez que el usuario escribe.
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = SecondaryText) },
                // `leadingIcon` es un icono que aparece al principio del campo de texto.
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = AccentColor
                    )
                },
                // Asegura que el texto no se divida en varias líneas.
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                // Ocupa todo el ancho disponible.
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardSurface,
                    unfocusedContainerColor = CardSurface,
                    focusedBorderColor = AccentColor,
                    unfocusedBorderColor = OutlineColor,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = AccentColor
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la contraseña.
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = SecondaryText) },
                // Icono de candado.
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = AccentColor
                    )
                },
                // Icono para mostrar/ocultar la contraseña.
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = SecondaryText
                        )
                    }
                },
                singleLine = true,
                // `visualTransformation` cambia la apariencia del texto (por ejemplo, a puntos para la contraseña).
                // Se alterna entre oculto y visible según el valor de `showPassword`.
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardSurface,
                    unfocusedContainerColor = CardSurface,
                    focusedBorderColor = AccentColor,
                    unfocusedBorderColor = OutlineColor,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = AccentColor
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para iniciar sesión.
            Button(
                onClick = {
                    // Se inicia una corrutina para realizar la operación de red sin congelar la app.
                    coroutine.launch {
                        // Muestra el indicador de carga.
                        isLoading = true
                        // Llama a la función de login del `userViewModel`.
                        val client = userViewModel.login(email, password)
                        // Oculta el indicador de carga.
                        isLoading = false

                        // Si el inicio de sesión es exitoso (`client` no es nulo)...
                        if (client != null) {
                            // Guarda los datos del usuario para mantener la sesión iniciada.
                            sessionManager.saveUserSession(
                                userId = client.id_client,
                                email = client.email_client,
                                name = client.name_client
                            )
                            // Navega a la pantalla de inicio (`Home`) y elimina la pantalla de login del historial,
                            // para que el usuario no pueda volver atrás.
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Correo o contraseña incorrectos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentColor
                ),
                // Esquinas redondeadas para el botón.
                shape = RoundedCornerShape(16.dp),
                // El botón se deshabilita mientras `isLoading` es verdadero.
                enabled = !isLoading
            ) {
                // Muestra un círculo de carga si `isLoading` es verdadero.
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else { // Si no, muestra el texto "Iniciar Sesión".
                    Text(
                        "Iniciar Sesión",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Texto con un botón para navegar a la pantalla de registro.
            TextButton(
                // Cuando se hace clic, navega a la pantalla de registro.
                onClick = { viewModel.navigateTo(Screen.Register) }
            ) {
                Text(
                    text = "¿No tienes cuenta? ",
                    color = SecondaryText,
                    fontSize = 14.sp
                )
                Text(
                    text = "Crear una",
                    color = AccentColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// El bloque de comentarios original con el análisis de estilos ha sido eliminado para mayor claridad
// y reemplazado por comentarios en línea a lo largo del código.