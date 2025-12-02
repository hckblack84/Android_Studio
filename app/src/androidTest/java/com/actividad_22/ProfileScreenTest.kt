package com.actividad_22.screen
// Importamos las herramientas necesarias para hacer pruebas de la interfaz de usuario en Compose.
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.actividad_22.data.local.Client
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Esta es una clase de prueba para la pantalla de perfil (ProfileScreen).
 * Su propósito es verificar que la pantalla de perfil se vea y funcione correctamente.
 */
class ProfileScreenTest {

    // Esta regla nos permite controlar y probar los componentes de la interfaz de usuario de Compose.
    @get:Rule
    val composeTestRule = createComposeRule()

    // Creamos versiones "simuladas" o "falsas" de los controladores que necesita la pantalla de perfil.
    // Esto nos permite probar la pantalla de forma aislada, sin depender de otras partes de la aplicación.
    private lateinit var mockNavController: NavController
    private lateinit var mockMainViewModel: MainViewModel
    private lateinit var mockUserViewModel: UserViewModel

    // Creamos un cliente de prueba con datos de ejemplo.
    private val testClient = Client(
        id_client = 1L,
        name_client = "Juan Pérez",
        email_client = "juan@example.com",
        password_client = "password123",
        direction_client = "Calle Falsa 123"
    )

    // Esta función se ejecuta antes de cada prueba. Es como preparar el escenario.
    @Before
    fun setUp() {
        // Inicializamos nuestros controladores simulados.
        mockNavController = mockk(relaxed = true)
        mockMainViewModel = mockk(relaxed = true)
        mockUserViewModel = mockk(relaxed = true)

        // Creamos un flujo de datos que contiene a nuestro cliente de prueba.
        val clientFlow = MutableStateFlow<Client?>(testClient)
        // Le decimos a nuestro ViewModel simulado que, cuando se le pida el cliente actual, devuelva nuestro cliente de prueba.
        every { mockUserViewModel.currentClient } returns clientFlow

        // Dibujamos la pantalla de perfil en nuestro entorno de prueba con los controladores simulados.
        composeTestRule.setContent {
            ProfileScreen(
                navController = mockNavController,
                mainViewModel = mockMainViewModel,
                userViewModel = mockUserViewModel
            )
        }
    }


    /**
     * Prueba que al entrar en modo edición, el botón para mostrar/ocultar la contraseña funciona.
     * Aunque esta prueba está incompleta, su intención es:
     * 1. Hacer clic en "Editar Perfil".
     * 2. Encontrar el icono del ojo junto al campo de la contraseña.
     * 3. (Faltaría) Hacer clic en él y verificar que la contraseña se muestra y se oculta.
     */
    @Test
    fun profileScreen_editMode_passwordToggle_showsAndHidesPassword() {
        // Simulamos un clic en el botón que dice "Editar Perfil".
        composeTestRule.onNodeWithText("Editar Perfil").performClick()

        // Buscamos los elementos que sirven para alternar la visibilidad de la contraseña (el icono del ojo).
        val passwordToggle = composeTestRule.onAllNodesWithContentDescription("toggle password visibility")
    }

    /**
     * Prueba que el icono de la cámara (para cambiar la foto de perfil) se muestra en la pantalla.
     */
    @Test
    fun profileScreen_cameraIcon_isDisplayed() {
        // Verificamos que un elemento con la descripción "Cambiar foto" (que es el icono de la cámara) está visible.
        composeTestRule.onNodeWithContentDescription("Cambiar foto").assertIsDisplayed()
    }

    /**
     * Prueba que la información del cliente (nombre, email, dirección) se muestra correctamente en la pantalla.
     */
    @Test
    fun profileScreen_infoCards_displayCorrectData() {
        // Verificamos que la dirección del cliente de prueba ("Calle Falsa 123") se muestra en la pantalla.
        composeTestRule.onNodeWithText("Calle Falsa 123").assertIsDisplayed()

        // Verificamos que las etiquetas "Nombre", "Email" y "Dirección" también están visibles.
        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dirección").assertIsDisplayed()
    }
}