package com.actividad_22.viewmodel

import androidx.lifecycle.ViewModel
import com.actividad_22.navigation.NavigationEvent
import com.actividad_22.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class MainViewModel : ViewModel(){
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()

    open val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()


    fun navigateTo(screen: Screen){
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.Navigateto(route = screen))
        }
    }
    fun navigateBack(){
        // Define una clase que hereda de ViewModel.
// Esto permite que sobreviva a cambios de configuración (como rotar la pantalla).
        class MainViewModel : ViewModel() {

            // 1. Un flujo de eventos de navegación MUTABLE y PRIVADO.
            // MutableSharedFlow es un tipo de Flow que permite emitir valores (eventos) a múltiples observadores.
            // Solo el ViewModel puede emitir nuevos eventos de navegación.
            private val _navigationEvents = MutableSharedFlow<NavigationEvent>()

            // 2. Una versión PÚBLICA e INMUTABLE del mismo flujo.
            // La UI (tus Composables) observará este `SharedFlow` para reaccionar a los eventos.
            // Al ser inmutable (no tiene el méthod `emit`), la UI no puede desencadenar eventos por sí misma,
            // garantizando que toda la lógica de navegación se origina en el ViewModel.
            val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()


            /**
             * 3. Función para solicitar la navegación a una pantalla específica.
             * @param screen Es un objeto (probablemente una sealed class o enum) que representa el destino.
             */
            fun navigateTo(screen: Screen) {
                // Se lanza una corrutina en el hilo principal (Dispatchers.Main),
                // ya que las interacciones con la UI deben ocurrir en este hilo.
                CoroutineScope(context = Dispatchers.Main).launch {
                    // Emite un evento `Navigateto` con la ruta del destino.
                    // La UI estará escuchando este evento y llamará al NavController para navegar.
                    _navigationEvents.emit(value = NavigationEvent.Navigateto(route = screen))
                }
            }

            /**
             * 4. Función para solicitar "volver atrás" en la pila de navegación.
             * Esto es equivalente a presionar el botón de retroceso del dispositivo.
             */
            fun navigateBack() {
                CoroutineScope(context = Dispatchers.Main).launch {
                    // Emite el evento `popBackStack`.
                    // La UI lo recibirá y ejecutará `navController.popBackStack()`.
                    _navigationEvents.emit(value = NavigationEvent.popBackStack)
                }
            }

            /**
             * 5. Función para solicitar la navegación "hacia arriba" en la jerarquía de la app.
             * Suele usarse con el botón de flecha en la TopAppBar.
             */
            fun navigateUp() {
                CoroutineScope(context = Dispatchers.Main).launch {
                    // Emite el evento `NavigateUp`.
                    // La UI lo recibirá y ejecutará `navController.navigateUp()`.
                    _navigationEvents.emit(value = NavigationEvent.NavigateUp)
                }
            }
        }
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.popBackStack)
        }
    }

    fun navigateUp(){
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.NavigateUp)
        }
    }
}