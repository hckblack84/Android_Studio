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

// Colores
private val DeepDarkBackground = Color(0xFF0F1218)
private val CardSurface = Color(0xFF1A1F2E)
private val AccentColor = Color(0xFFF38A1D)
private val SecondaryText = Color(0xFF8B92A8)
private val OutlineColor = Color(0xFF2A3142)

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val coroutine = rememberCoroutineScope()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Logo
            Box(
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
                Image(
                    painter = painterResource(id = R.drawable.factorio),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Título
            Text(
                text = "Bienvenido",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Inicia sesión para continuar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = SecondaryText
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = SecondaryText) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = AccentColor
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

            // Campo Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = SecondaryText) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = AccentColor
                    )
                },
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

            // Botón Login
            Button(
                onClick = {
                    coroutine.launch {
                        isLoading = true
                        val client = userViewModel.login(email, password)
                        isLoading = false

                        if (client != null) {
                            // Guardar sesión
                            sessionManager.saveUserSession(
                                userId = client.id_client,
                                email = client.email_client,
                                name = client.name_client
                            )
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
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        "Iniciar Sesión",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Registro
            TextButton(
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

/*
* ANÁLISIS DE ESTILOS UTILIZADOS EN LoginScreen
*
* GAMA DE COLORES (Paleta Oscura "Dark Mode"):
* - DeepDarkBackground (0xFF0F1218): Un azul muy oscuro, casi negro, usado para el fondo principal de la pantalla.
* - CardSurface (0xFF1A1F2E): Un azul oscuro grisáceo, usado como fondo para los campos de texto (OutlinedTextField).
* - AccentColor (0xFFF38A1D): Un naranja vibrante, utilizado como color de acento principal. Se usa en:
*   - El resplandor radial detrás del logo.
*   - Los iconos dentro de los campos de texto (Email, Lock).
*   - El borde de los campos de texto cuando están enfocados.
*   - El color del cursor en los campos de texto.
*   - El color de fondo del botón principal "Iniciar Sesión".
*   - El texto "Crear una" para incitar al registro.
* - SecondaryText (0xFF8B92A8): Un gris azulado claro, para textos secundarios y elementos de menor énfasis. Se usa en:
*   - El subtítulo "Inicia sesión para continuar".
*   - Las etiquetas (label) de los campos de texto.
*   - El icono de visibilidad de la contraseña.
*   - El texto "¿No tienes cuenta? ".
* - OutlineColor (0xFF2A3142): Un azul grisáceo, para los bordes de los campos de texto cuando no están enfocados.
* - Color.White (0xFFFFFFFF): Blanco puro, para textos principales de alta visibilidad. Se usa en:
*   - El título "Bienvenido".
*   - El texto introducido por el usuario en los campos de texto.
*   - El texto del botón "Iniciar Sesión".
*   - El indicador de carga (CircularProgressIndicator).
* - Color.Transparent (0x00000000): Usado en el degradado del logo para crear un efecto de desvanecimiento.
*
* FUENTES (Tipografía):
* - Se utiliza la fuente por defecto del sistema Android (Roboto).
* - Pesos de fuente (FontWeight):
*   - FontWeight.Bold: Para textos que requieren mayor énfasis, como el título "Bienvenido", el texto del botón "Iniciar Sesión" y el enlace "Crear una".
*   - FontWeight.Normal: Para textos con énfasis estándar, como el subtítulo.
* - Tamaños de fuente (fontSize):
*   - 32.sp: Para el título principal ("Bienvenido").
*   - 18.sp: Para el texto del botón principal.
*   - 16.sp: Para el subtítulo.
*   - 14.sp: Para el texto del botón de registro ("¿No tienes cuenta? Crear una").
*
* FORMAS Y BORDES (Shapes):
* - RoundedCornerShape(60.dp): Se usa para crear un círculo perfecto para el fondo del logo (dado que el tamaño del Box es 120.dp, un radio de la mitad del tamaño crea un círculo).
* - RoundedCornerShape(16.dp): Se aplica a los campos de texto (OutlinedTextField) y al botón principal "Iniciar Sesión" para darles esquinas notablemente redondeadas.
*
* EFECTOS VISUALES:
* - Brush.radialGradient: Se utiliza para crear un efecto de resplandor o aura detrás del logo, usando el `AccentColor` con transparencia y desvaneciéndose a `Color.Transparent`.
*
* ICONOGRAFÍA:
* - Se utilizan iconos predefinidos de Material Icons:
*   - `Icons.Default.Email`: Para el campo de correo.
*   - `Icons.Default.Lock`: Para el campo de contraseña.
*   - `Icons.Default.Visibility` / `Icons.Default.VisibilityOff`: Para alternar la visibilidad de la contraseña.
*/