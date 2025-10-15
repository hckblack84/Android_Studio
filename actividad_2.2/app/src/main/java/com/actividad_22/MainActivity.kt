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
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.screen.ProfileScreen
import com.actividad_22.screen.RegisterScreen
import com.actividad_22.screen.SettingsScreen
import com.actividad_22.screen.StoreScreen
import com.actividad_22.screen.UsScreen
import com.actividad_22.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest


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
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable(route = Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Profile.route) {
                            ProfileScreen(navController = navController, viewModel = viewModel)
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
                            val userViewModel: UserViewModel = viewModel()  // ✅ AGREGADO
                            RegisterScreen(navController = navController, viewModel = userViewModel)  // ✅ CORREGIDO
                        }
                    }
                }
            }
        }
    }
}



