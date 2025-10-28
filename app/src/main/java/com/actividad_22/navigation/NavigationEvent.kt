package com.actividad_22.navigation


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
/*
*
* */