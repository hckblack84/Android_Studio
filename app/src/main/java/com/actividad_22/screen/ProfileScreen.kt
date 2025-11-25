package com.actividad_22.screen

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.actividad_22.data.local.Client
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.SessionManager
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

// Colores
private val DeepDarkBackground = Color(0xFF0F1218)
private val CardSurface = Color(0xFF1A1F2E)
private val AccentColor = Color(0xFFF38A1D)
private val SecondaryText = Color(0xFF8B92A8)
private val OutlineColor = Color(0xFF2A3142)

@Composable
fun ProfileScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val currentClient by userViewModel.currentClient.collectAsState()
    var isEditMode by remember { mutableStateOf(false) }

    // Estados de la cámara
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // Estados para edición
    var editName by remember { mutableStateOf("") }
    var editEmail by remember { mutableStateOf("") }
    var editPassword by remember { mutableStateOf("") }
    var editDirection by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Cargar cliente al iniciar si hay sesión
    LaunchedEffect(Unit) {
        val userId = sessionManager.getUserId()
        if (userId != -1L) {
            userViewModel.loadClientById(userId)
        }
    }

    // Actualizar campos cuando cambie el cliente
    LaunchedEffect(currentClient) {
        currentClient?.let {
            editName = it.name_client
            editEmail = it.email_client
            editPassword = it.password_client
            editDirection = it.direction_client
        }
    }

    // Launchers de cámara y galería
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tempPhotoUri
            Toast.makeText(context, "Foto guardada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error al tomar foto", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            Toast.makeText(context, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showDialog = true
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .verticalScroll(scrollState)
        ) {
            // Header del perfil con foto
            ProfileHeaderWithCamera(
                client = currentClient,
                imageUri = imageUri,
                onEditClick = { isEditMode = !isEditMode },
                onCameraClick = {
                    when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                        android.content.pm.PackageManager.PERMISSION_GRANTED -> showDialog = true
                        else -> permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                isEditMode = isEditMode
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (currentClient != null) {
                if (isEditMode) {
                    // Modo edición
                    EditProfileForm(
                        name = editName,
                        onNameChange = { editName = it },
                        email = editEmail,
                        onEmailChange = { editEmail = it },
                        password = editPassword,
                        onPasswordChange = { editPassword = it },
                        direction = editDirection,
                        onDirectionChange = { editDirection = it },
                        showPassword = showPassword,
                        onTogglePassword = { showPassword = !showPassword },
                        onSave = {
                            coroutineScope.launch {
                                // Aquí se usa .copy()
                                val updatedClient = currentClient!!.copy(
                                    name_client = editName,
                                    email_client = editEmail,
                                    password_client = editPassword,
                                    direction_client = editDirection
                                )
                                userViewModel.updateCurrentClient(updatedClient)
                                sessionManager.saveUserSession(
                                    userId = updatedClient.id_client,
                                    email = updatedClient.email_client,
                                    name = updatedClient.name_client
                                )
                                isEditMode = false
                                Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onCancel = {
                            currentClient?.let {
                                editName = it.name_client
                                editEmail = it.email_client
                                editPassword = it.password_client
                                editDirection = it.direction_client
                            }
                            isEditMode = false
                        }
                    )
                } else {
                    // Modo visualización
                    ProfileInfo(client = currentClient!!)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Opciones del perfil
                    ProfileOptions(
                        onLogout = {
                            sessionManager.clearSession()
                            userViewModel.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            } else {
                // No hay usuario logueado
                NoUserLoggedIn(
                    onLoginClick = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }


        FloatingBottomBar(
            onHomeClick = { mainViewModel.navigateTo(Screen.Home) },
            onEventClick = { mainViewModel.navigateTo(Screen.Event) },
            onCartClick = { mainViewModel.navigateTo(Screen.Cart) },
            onStoreClick = { mainViewModel.navigateTo(Screen.Store) },
            onProfileClick = { mainViewModel.navigateTo(Screen.Profile) },
            modifier = Modifier.align(Alignment.BottomCenter)
        )


    }

    // Diálogo de selección de imagen
    if (showDialog) {
        ImagePickerDialog(
            onDismiss = { showDialog = false },
            onCameraClick = {
                val uri = context.createImageUri()
                tempPhotoUri = uri
                cameraLauncher.launch(uri)
                showDialog = false
            },
            onGalleryClick = {
                galleryLauncher.launch("image/*")
                showDialog = false
            }
        )
    }
}

// RESTO DE LOS COMPOSABLES (ProfileHeaderWithCamera, ImagePickerDialog, ProfileInfo, InfoCard, EditProfileForm, ProfileOptions, NoUserLoggedIn, createImageUri)

@Composable
fun ProfileHeaderWithCamera(
    client: Client?,
    imageUri: Uri?,
    onEditClick: () -> Unit,
    onCameraClick: () -> Unit,
    isEditMode: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        CardSurface,
                        DeepDarkBackground
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Avatar con cámara
        Box(
            modifier = Modifier.size(120.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentColor.copy(alpha = 0.3f),
                                AccentColor.copy(alpha = 0.1f)
                            )
                        )
                    )
                    .clickable(onClick = onCameraClick),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = AccentColor,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            // Botón de cámara en la esquina
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(AccentColor)
                    .clickable(onClick = onCameraClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Cambiar foto",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre
        Text(
            text = client?.name_client ?: "",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Email
        Text(
            text = client?.email_client ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = SecondaryText
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón editar
        if (client != null) {
            Button(
                onClick = onEditClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEditMode) CardSurface else AccentColor
                ),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = if (isEditMode) Icons.Default.Close else Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEditMode) "Cancelar" else "Editar Perfil",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ImagePickerDialog(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardSurface,
        icon = {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(AccentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = AccentColor
                )
            }
        },
        title = {
            Text(
                text = "Seleccionar foto de perfil",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onCameraClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentColor
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("Tomar foto", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = onGalleryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(AccentColor, AccentColor))
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = AccentColor
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("Elegir de galería", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = SecondaryText, fontWeight = FontWeight.SemiBold)
            }
        }
    )
}

@Composable
fun ProfileInfo(client: Client) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Información Personal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoCard(
            icon = Icons.Default.Person,
            label = "Nombre",
            value = client.name_client
        )

        InfoCard(
            icon = Icons.Default.Email,
            label = "Email",
            value = client.email_client
        )

        InfoCard(
            icon = Icons.Default.LocationOn,
            label = "Dirección",
            value = client.direction_client
        )

        InfoCard(
            icon = Icons.Default.Lock,
            label = "Contraseña",
            value = "••••••••"
        )
    }
}

@Composable
fun InfoCard(
    icon: ImageVector,
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AccentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AccentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = SecondaryText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun EditProfileForm(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    direction: String,
    onDirectionChange: (String) -> Unit,
    showPassword: Boolean,
    onTogglePassword: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Editar Información",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nombre", color = SecondaryText) },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null, tint = AccentColor)
            },
            singleLine = true,
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

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email", color = SecondaryText) },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null, tint = AccentColor)
            },
            singleLine = true,
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

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña", color = SecondaryText) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = AccentColor)
            },
            trailingIcon = {
                IconButton(onClick = onTogglePassword) {
                    Icon(
                        if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = SecondaryText
                    )
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
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

        OutlinedTextField(
            value = direction,
            onValueChange = onDirectionChange,
            label = { Text("Dirección", color = SecondaryText) },
            leadingIcon = {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = AccentColor)
            },
            singleLine = true,
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

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.linearGradient(listOf(OutlineColor, OutlineColor))
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Cancelar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = onSave,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentColor
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun ProfileOptions(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Opciones",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFF4444).copy(alpha = 0.1f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF4444).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = Color(0xFFFF4444),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Cerrar Sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF4444)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Salir", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun NoUserLoggedIn(onLoginClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(CardSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = SecondaryText,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No has iniciado sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicia sesión para ver tu perfil",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = SecondaryText
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Iniciar Sesión",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun Context.createImageUri(): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "PROFILE_$timeStamp.jpg"
    val image = File(filesDir, imageFileName)
    return FileProvider.getUriForFile(
        this,
        "${packageName}.fileprovider",
        image
    )
}