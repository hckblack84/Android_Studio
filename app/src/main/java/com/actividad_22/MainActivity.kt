package com.actividad_22

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.actividad_22.navigation.NavigationEvent
import com.actividad_22.navigation.Screen
import com.actividad_22.ui.theme.Actividad_22Theme
import com.actividad_22.screen.EventScreen
import com.actividad_22.screen.HomeScreen
import com.actividad_22.screen.LoginScreen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.screen.ProfileScreen
import com.actividad_22.screen.RegisterScreen
import com.actividad_22.screen.SettingsScreen
import com.actividad_22.screen.StartScreen
import com.actividad_22.screen.StoreScreen
import com.actividad_22.screen.UsScreen
import com.actividad_22.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest




//Recordar actualizar el ide en la parte superior derecha
/*
* Donde esta en engranaje
* para evitar que salga el error
* de que a version esta desactualizada
* */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Actividad_22Theme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                LaunchedEffect(key1 = Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.Navigateto -> {
                                navController.navigate(event.route.route) {
                                    event.popUpToRoute?.let {
                                        popUpTo(it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }

                            is NavigationEvent.popBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Start.route,  // al iniciar la pagina comenzara directamente en la pagina de start screen
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Rutas
                        /*
                        * Cada composable almacena una ruta por difeccto que se encuentra
                        * entre los parentesis , para poder agregar una ruta se  debe agregar la direccion en
                        * el apartado de *Screen* en la carpeta de navigation
                        * la primera de todas
                        * */
                        composable(route = Screen.Start.route) {
                            StartScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Profile.route) {
                            val userViewModel: UserViewModel = viewModel()
                            ProfileScreen(
                                navController = navController,
                                viewModel = viewModel,
                                userViewModel = userViewModel
                            )/*

                             cada perdil que maneje una vista de usuario se le debe
                             incorportar el userViewModel para evitar que la informacion
                             que se encuentre dentro de esta sea manejada de la forma correcta
                            */
                        }

                        composable(route = Screen.Settings.route) {
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Store.route) {
                            StoreScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Us.route) {
                            UsScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Event.route) {
                            EventScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Register.route) {
                            val userViewModel: UserViewModel = viewModel()
                            RegisterScreen(navController = navController, userViewModel = userViewModel)
                        }

                        composable(route = Screen.Login.route) {
                            val userViewModel: UserViewModel = viewModel()
                            LoginScreen(
                                navController = navController,
                                viewModel = viewModel,
                                userViewModel = userViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}