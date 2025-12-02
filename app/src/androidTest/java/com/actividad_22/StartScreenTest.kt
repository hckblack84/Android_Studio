package com.actividad_22

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import com.actividad_22.navigation.Screen
import com.actividad_22.screen.StartScreen
import com.actividad_22.viewmodel.MainViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Esta clase se encarga de hacer pruebas a la pantalla de inicio (StartScreen)
 * para asegurarse de que todo funciona como se espera.
 */
class StartScreenTest {

    /**
     * Prepara el entorno necesario para poder probar componentes visuales de la app.
     */
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Prueba que la pantalla de inicio se muestra correctamente con todos sus textos.
     */
    @Test
    fun startScreenAppears() {
        // Carga la pantalla de inicio en el entorno de prueba.
        composeRule.setContent {
            StartScreen(
                navController = TestNavHostController(composeRule.activity),
                viewModel = FakeViewModel()
            )
        }

        // Verifica que los textos esperados están presentes en la pantalla.
        composeRule.onNodeWithText("Iniciar Sesion").assertExists()
        composeRule.onNodeWithText("Registrarse").assertExists()
        composeRule.onNodeWithText("Acceder como invitado").assertExists()
        composeRule.onNodeWithText("Bienvenido a\nLevel Up Gaming").assertExists()
    }

    /**
     * Prueba que al hacer clic en los botones "Iniciar Sesion" y "Registrarse",
     * se intenta navegar a las pantallas correctas.
     */
    @Test
    fun navigateToLoginEvent() {
        // Usamos un "ViewModel falso" para poder verificar las acciones de navegación.
        val fakeVM = FakeViewModel()

        // Carga la pantalla de inicio con nuestro ViewModel falso.
        composeRule.setContent {
            StartScreen(
                navController = TestNavHostController(composeRule.activity),
                viewModel = fakeVM
            )
        }

        // Simula un clic en el botón "Iniciar Sesion".
        composeRule.onNodeWithText("Iniciar Sesion").performClick()
        // Comprueba que se intentó ir a la pantalla de Login.
        assertEquals(Screen.Login, fakeVM.lastRoute)

        // Simula un clic en el botón "Registrarse".
        composeRule.onNodeWithText("Registrarse").performClick()
        // Comprueba que se intentó ir a la pantalla de Registro.
        assertEquals(Screen.Register, fakeVM.lastRoute)
    }
}

/**
 * Esta es una versión "falsa" o de prueba del MainViewModel.
 * Nos ayuda a simular el comportamiento del ViewModel real durante las pruebas
 * sin necesidad de tener toda la lógica completa.
 */
class FakeViewModel : MainViewModel() {
    // Guarda la última ruta a la que se intentó navegar.
    var lastRoute: Screen? = null

    /**
     * En lugar de navegar de verdad, esta función solo guarda la ruta
     * para que podamos comprobar a dónde se quiso ir.
     */
    override fun navigateTo(route: Screen) {
        lastRoute = route
    }
}
