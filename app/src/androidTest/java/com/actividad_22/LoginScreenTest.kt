package com.actividad_22

/** Este archivo contiene pruebas para la pantalla de inicio de sesión (LoginScreen). */
// In app/src/test/java/com/actividad_22/screen/LoginScreenTest.ktpackage com.actividad_22.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.actividad_22.screen.LoginScreen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Define una clase de prueba para la pantalla de inicio de sesión (LoginScreen).
 * Su objetivo es asegurarse de que la interfaz de usuario se comporte como se espera.
 */
class LoginScreenTest {

    // Prepara el entorno necesario para probar componentes de la interfaz de usuario de Compose.
    @get:Rule
    val composeTestRule = createComposeRule()

    // Versiones "falsas" o simuladas de las clases que necesita LoginScreen para funcionar.
    // Las usamos para controlar el comportamiento y verificar las interacciones durante la prueba.
    private lateinit var mockNavController: NavController
    private lateinit var mockMainViewModel: MainViewModel
    private lateinit var mockUserViewModel: UserViewModel

    /**
     * Esta función se ejecuta antes de cada prueba.
     * Se encarga de inicializar las versiones simuladas y de cargar la pantalla de inicio de sesión.
     */
    @Before
    fun setUp() {
        // Crea las versiones simuladas de nuestras clases. 'relaxed = true' permite que ignoren llamadas que no nos interesan para la prueba.
        mockNavController = mockk(relaxed = true)
        mockMainViewModel = mockk(relaxed = true)
        mockUserViewModel = mockk(relaxed = true)

        // Carga la pantalla de LoginScreen en el entorno de prueba con las dependencias simuladas.
        composeTestRule.setContent {
            LoginScreen(
                navController = mockNavController, // El controlador de navegación falso.
                viewModel = mockMainViewModel, // El modelo de vista principal falso.
                userViewModel = mockUserViewModel // El modelo de vista de usuario falso.
            )
        }
    }

    /**
     * Prueba para verificar que todos los elementos iniciales de la pantalla de inicio de sesión se muestran correctamente.
     */
    @Test
    fun loginScreen_initialState_displaysAllElements() {
        // 1. Comprueba si el título "Bienvenido" es visible.
        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()

        // 2. Comprueba si el campo para el correo electrónico es visible.
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()

        // 3. Comprueba si el campo para la contraseña es visible.
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // 4. Comprueba si el botón "Iniciar Sesión" es visible y se puede pulsar.
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed().assertIsEnabled()

        // 5. Comprueba si el botón para crear una cuenta ("Crear una") es visible.
        composeTestRule.onNodeWithText("Crear una").assertIsDisplayed()
    }

    /**
     * Prueba que al hacer clic en el botón "Iniciar Sesión" con datos válidos, se llama a la función de login.
     */
    @Test
    fun loginButton_whenClickedWithValidInput_callsViewModel() {
        // Preparación: Le decimos al modelo de vista falso cómo debe comportarse cuando se llame a su función 'login'.
        coEvery { mockUserViewModel.login(any(), any()) } returns null // Simulamos que el login falla para centrarnos solo en la llamada.

        // Act: Find the input fields and perform actions
        val emailField = composeTestRule.onNodeWithText("Correo electrónico")
        val passwordField = composeTestRule.onNodeWithText("Contraseña")
        val loginButton = composeTestRule.onNodeWithText("Iniciar Sesión")

        // Type text into the fields
        emailField.performTextInput("test@example.com")
        passwordField.performTextInput("password123")

        // Hacemos clic en el botón de iniciar sesión.
        loginButton.performClick()

        // Verificación: Confirmamos que la función 'login' del modelo de vista de usuario fue llamada con los datos que escribimos.
        verify { runBlocking { mockUserViewModel.login("test@example.com", "password123") } }
    }


}


