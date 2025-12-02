// Define el paquete al que pertenece este archivo.
package com.actividad_22

// Importa las clases y funciones necesarias de otras partes del proyecto y de las bibliotecas.
import com.actividad_22.navigation.NavigationEvent
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// Indica que se están utilizando funciones experimentales de corutinas de Kotlin.
@OptIn(ExperimentalCoroutinesApi::class)
// Esta clase contiene pruebas para verificar que la navegación funciona como se espera.
class HomeScreenNavigationTest {

    // Crea un despachador de tareas especial para las pruebas, que permite controlar cómo y cuándo se ejecutan.
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    // Declara una variable para el MainViewModel, que es el cerebro de la pantalla principal.
    private lateinit var mainViewModel: MainViewModel

    // Esta función se ejecuta antes de cada prueba.
    @Before
    fun setUp() {
        // Configura el despachador de pruebas como el principal, para tener control sobre las corutinas.
        Dispatchers.setMain(testDispatcher)
        // Crea una nueva instancia del MainViewModel para cada prueba.
        mainViewModel = MainViewModel()
    }

    // Esta función se ejecuta después de cada prueba.
    @After
    fun tearDown() {
        // Limpia y restaura el despachador principal original para no afectar otras pruebas.
        Dispatchers.resetMain()
    }

    // Esta función especial espera y captura el primer "evento de navegación" que se produzca.
    // Un evento de navegación es como una señal que dice "ve a esta pantalla".
    private suspend fun getEmittedEvent(scope: TestScope): NavigationEvent {
        // Pausa la ejecución actual y espera a que ocurra algo.
        return suspendCoroutine { continuation ->
            // Inicia una tarea en segundo plano para no bloquear nada.
            scope.backgroundScope.launch {
                try {
                    // Espera a que el MainViewModel emita su primer evento de navegación.
                    val event = mainViewModel.navigationEvents.first()
                    // Cuando lo recibe, reanuda la ejecución y devuelve el evento.
                    continuation.resume(event)
                } catch (e: Exception) {
                    // Si algo sale mal, imprime un mensaje de error.
                    println("Error al recolectar evento de navegación: ${e.message}")
                }
            }
        }
    }

    // Esto es una prueba específica.
    // Verifica que cuando se intenta navegar a la pantalla de inicio (Home), se genera el evento correcto.
    @Test
    fun navigateToHomeScreen_emiteEventoCorrecto() = runTest(testDispatcher) {
        // Define la pantalla de destino, en este caso, la pantalla de inicio.
        val homeScreen = Screen.Home

        // Inicia una tarea en segundo plano que se queda esperando a que se emita un evento de navegación.
        // Usamos la función que creamos antes para esto.
        val deferredEvent = backgroundScope.launch {
            getEmittedEvent(this@runTest)
        }

        // (Aquí faltaría el código que dispara la navegación y luego comprueba que el evento recibido es el correcto)

    }

}