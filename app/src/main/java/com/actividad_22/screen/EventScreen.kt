package com.actividad_22.screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel


/**
 *  composable function muestra la pagina "Events" screen de la app.
 *
 *
 * @param navController se utiliza para la navegacion entre pantallas
 * @param viewModel el main view model permite un mejor manejo de entidades y pantallas de la app
 */
@Composable
fun EventScreen(
    navController: NavController,
    viewModel: MainViewModel
){
    // Columna principal que ocupa toda la pantalla y centra su contenido superior.
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        // Texto que muestra el título de la pantalla.
        Text(text = "¡Eventos Proximos!",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
            )
        // Espaciador vertical para separar el título del contenido.
        Spacer(modifier= Modifier.height(20.dp) )
        // Texto que describe el próximo evento.
        Text(
            text = "Proximo Evento a las afueras de la casa de marquitos " +
                    "habra juegos de azar y mujerzuelas "
        )
    }


    /**
     * Columna secundaria que se alinea en la parte inferior y final de la pantalla.
     * Se utiliza para colocar el botón de navegación.
     */
    Column (modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom){
        // Botón que, al ser presionado, navega de regreso a la pantalla de inicio (Home).
        Button(
            onClick = {
                viewModel.navigateTo(screen = Screen.Home)
            }
        ) {
            Text(text = "Volver al inicio")
        }
    }
}