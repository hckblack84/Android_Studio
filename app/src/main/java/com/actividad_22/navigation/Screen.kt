package com.actividad_22.navigation

/**

 * representa de diferenctes maneras la navegacion entre pantallas de la aplicacion
 * el formato de seadle class permite un manejo seguro de este tipo de navegacion
 *
 * @property route The unique string identifier for the navigation route.
 */
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
    data object Cart : Screen(route = "Cart_page")
    data object Post : Screen(route = "Post_Page")
    data object PostCart : Screen(route = "PostCart_Page")
}

//Permite saber el parametro de la ruta para comenzar con la navegacion de manera segura evitando bucles


    data class Details(val itemId: String): Screen(route = "detail_page/{itemId}"){
        fun builRoute(): String{
            return route.replace(oldValue = "{itemId}", newValue = itemId)
        }
    }
