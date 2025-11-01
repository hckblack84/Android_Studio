package com.actividad_22.navigation.appNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.actividad_22.screen.RegisterScreen
import com.actividad_22.screen.SummaryScreen
import com.actividad_22.viewmodel.UserViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "Register"
    ){
        composable(route = "Register"){
            RegisterScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

    }
}