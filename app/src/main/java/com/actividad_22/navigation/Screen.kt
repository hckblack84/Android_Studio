package com.actividad_22.navigation

sealed class Screen(val route : String){
    data object Home : Screen(route = "home_page")
    data object Profile : Screen(route = "profile_page")
    data object Settings : Screen(route = "settings_page")

    data object Store : Screen(route = "Store_Page")

    data object  Us : Screen(route = "Us_Page" )

    data object  Event : Screen(route = "Event_page")

    data object Start : Screen(route = "Start_page")

    data object Login : Screen(route = "Login_page")

    data object Register : Screen(route = "Register_page")


    data object Summary : Screen(route = "Summary_page")
}


    data class Details(val itemId: String): Screen(route = "detail_page/{itemId}"){
        fun builRoute(): String{
            return route.replace(oldValue = "{itemId}", newValue = itemId)
        }
    }
