package com.actividad_22.navigation


/**
 * Representa los eventos que desencadenan acciones de navegación dentro de la aplicación.
 * Esta clase sellada (sealed class) define todos los posibles comandos de navegación que la
 * interfaz de usuario (UI) puede enviar a un gestor de navegación central (por ejemplo, un ViewModel
 * o un navegador dedicado).
 */
sealed class NavigationEvent{


    data class Navigateto(
        val route: Screen,
        val popUpToRoute: Screen? =null,
        val inclusive : Boolean = false,
        val singleTop : Boolean = false
    ): NavigationEvent()

object popBackStack : NavigationEvent()

object NavigateUp: NavigationEvent()


}
