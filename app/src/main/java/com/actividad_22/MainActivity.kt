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
import com.actividad_22.screen.PostCartScreen
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

/**
 * MainActivity es la pantalla principal de la aplicación.
 * Configura la navegación y las bases de datos necesarias para que todo funcione.
 */
class MainActivity : ComponentActivity() {
    /**
     * onCreate se llama cuando la actividad se crea por primera vez.
     * Aquí se configura la interfaz de usuario y se inicializa todo.
     * @param savedInstanceState Si la actividad se está recreando, este paquete contiene los datos que guardó anteriormente.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permite que la aplicación ocupe toda la pantalla, incluyendo el área de la barra de estado.
        enableEdgeToEdge()
        // setContent define la interfaz de usuario de la actividad utilizando Jetpack Compose.
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

                // Prepara el gestor de datos para las publicaciones.
                val postViewModel:PostViewModel = viewModel()




                // Este bloque se ejecuta una sola vez y se encarga de escuchar los eventos de navegación.
                // Cuando el viewModel pide navegar a otra pantalla, este código lo hace posible.
                LaunchedEffect(key1 = Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            // Si el evento es para navegar a una ruta específica.
                            is NavigationEvent.Navigateto -> {
                                navController.navigate(event.route.route) {
                                    // Opcionalmente, puede limpiar el historial de navegación hasta una pantalla específica.
                                    event.popUpToRoute?.let {
                                        popUpTo(it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    // Evita crear múltiples copias de la misma pantalla.
                                    launchSingleTop = event.singleTop
                                    // Restaura el estado de la pantalla si volvemos a ella.
                                    restoreState = true
                                }
                            }

                            // Si el evento es para volver a la pantalla anterior.
                            is NavigationEvent.popBackStack -> navController.popBackStack()
                            // Si el evento es para subir un nivel en la jerarquía de navegación.
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                // Scaffold es un diseño básico que proporciona una estructura para la pantalla
                // (como barras superiores, cajones de navegación, etc.).
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Start.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Define la pantalla de inicio.
                        composable(route = Screen.Start.route) {
                            StartScreen(navController = navController, viewModel = viewModel)
                        }

                        // Define la pantalla principal después de iniciar sesión.
                        composable(route = Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }

                        // Define la pantalla de perfil del usuario.
                        composable(route = Screen.Profile.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            ProfileScreen(
                                navController = navController,
                                mainViewModel = viewModel, // Pasa el gestor de datos principal.
                                userViewModel = userViewModel
                            )
                        }

                        // Define la pantalla de configuración.
                        composable(route = Screen.Settings.route) {
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }

                        // Define la pantalla de la tienda.
                        composable(route = Screen.Store.route) {
                            val postViewModel: PostViewModel = viewModel()
                            StoreScreen(navController = navController, viewModel = viewModel, productViewModel = productViewModel, postViewModel = postViewModel)
                        }

                        // Define una ruta para mostrar productos por categoría (actualmente sin usar).
                        composable(route = "category/{categoryId}") { backStackEntry ->
                            val categoryId = backStackEntry.arguments?.getString("categoryId")

                        }


                        // Define la pantalla de eventos.
                        composable(route = Screen.Event.route) {
                            EventScreen(navController = navController, viewModel = viewModel)
                        }

                        // Define la pantalla de registro de nuevos usuarios.
                        composable(route = Screen.Register.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            RegisterScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }

                        // Define la pantalla de inicio de sesión.
                        composable(route = Screen.Login.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            LoginScreen(
                                navController = navController,
                                viewModel = viewModel,
                                userViewModel = userViewModel
                            )
                        }
                        // Define la pantalla de resumen.
                        composable(Screen.Summary.route) {
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            SummaryScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }
                        // Define la pantalla del carrito de compras.
                        composable (Screen.Cart.route){
                            val userViewModel: UserViewModel = viewModel(factory = userFactory)
                            CartScreen(
                                navController = navController,
                                userViewModel = userViewModel,
                                viewModel = viewModel,
                                productViewModel = productViewModel,
                                postViewModel = postViewModel
                            )
                        }
                        // Define la pantalla para ver una publicación específica.
                        composable (Screen.Post.route){
                            PostScreen(postViewModel, viewModel)
                        }
                        // Define la pantalla para ver una publicación agregada al carrito.
                        composable (Screen.PostCart.route) {
                            PostCartScreen(
                                navController = navController,
                                viewModel = viewModel,
                                postViewModel = postViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

