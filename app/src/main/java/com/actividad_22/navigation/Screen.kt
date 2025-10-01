package com.actividad_22.navigation

sealed class Screen(val route : String){
    data object Home : Screen(route = "home_page")
    data object Profile : Screen(route = "profile_page")
    data object Settings : Screen(route = "settings_page")

    data class Details(val itemId: String): Screen(route = "detail_page/{itemId}"){
        fun builRoute(): String{
            return route.replace(oldValue = "{itemId}", newValue = itemId)
        }
    }
}