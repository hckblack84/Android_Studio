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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.actividad_22.data.local.ClientDataBase
import com.actividad_22.data.local.ProductDataBase
import com.actividad_22.data.repository.ClientRepository
import com.actividad_22.data.repository.ProductRepository
import com.actividad_22.navigation.NavigationEvent
import com.actividad_22.navigation.Screen
import com.actividad_22.screen.CartScreen
import com.actividad_22.ui.theme.Actividad_22Theme
import com.actividad_22.screen.EventScreen
import com.actividad_22.screen.HomeScreen
import com.actividad_22.screen.LoginScreen
import com.actividad_22.screen.PostScreen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.screen.ProfileScreen
import com.actividad_22.screen.RegisterScreen
import com.actividad_22.screen.SettingsScreen
import com.actividad_22.screen.StartScreen
import com.actividad_22.screen.StoreScreen
import com.actividad_22.screen.SummaryScreen
import com.actividad_22.viewmodel.PostViewModel
import com.actividad_22.viewmodel.ProductViewModel
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

                // Inicializar base de datos de clientes
                val clientDatabase = ClientDataBase.getDataBase(this)
                val userRepository = ClientRepository(clientDatabase.clientDao())
                val userFactory = UserViewModel.ClientViewModelFactory(userRepository)

                // Inicializar base de datos de productos
                val productDatabase = ProductDataBase.getDataBase(this)
                val productRepository = ProductRepository(productDatabase.productDao())
                val productViewModel: ProductViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return ProductViewModel(productRepository) as T
                        }
                    }
                )



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
                        startDestination = Screen.Start.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Start.route) {
                            StartScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Profile.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            ProfileScreen(
                                navController = navController,
                                mainViewModel = viewModel, // <--- ¡CORRECCIÓN APLICADA AQUÍ!
                                userViewModel = userViewModel
                            )
                        }

                        composable(route = Screen.Settings.route) {
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Store.route) {
                            StoreScreen(navController = navController, viewModel = viewModel, productViewModel = productViewModel)
                        }

                        composable(route = "category/{categoryId}") { backStackEntry ->
                            val categoryId = backStackEntry.arguments?.getString("categoryId")

                        }



                        composable(route = Screen.Event.route) {
                            EventScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(route = Screen.Register.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            RegisterScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }

                        composable(route = Screen.Login.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            LoginScreen(
                                navController = navController,
                                viewModel = viewModel,
                                userViewModel = userViewModel
                            )
                        }
                        composable(Screen.Summary.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            SummaryScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }
                        composable (Screen.Cart.route){
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            CartScreen(
                                navController = navController,
                                userViewModel = userViewModel,
                                viewModel = viewModel,
                                productViewModel = productViewModel
                            )
                        }
                        composable (Screen.Post.route){
                            val postViewModel: PostViewModel = viewModel()
                            PostScreen(postViewModel)
                        }
                    }
                }
            }
        }
    }
}

