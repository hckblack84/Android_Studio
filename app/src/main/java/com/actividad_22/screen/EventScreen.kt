package com.actividad_22.screen
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.actividad_22.R
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import java.util.Date


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

    val context = LocalContext.current

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo1),
                contentDescription = "Fondo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

    // Columna principal que ocupa toda la pantalla y centra su contenido superior.
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        // Texto que muestra el título de la pantalla.
        Text(text = "¡Eventos Proximos!",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.error


            )
        Text(text = "Marquitos House",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colorScheme.surfaceDim.copy(0.7f)
        )
        // Espaciador vertical para separar el título del contenido.
        Spacer(modifier= Modifier.height(20.dp) )

        // Texto que describe el próximo evento.
        Text(
            text = "Evento formal enfocado en festejar la primera junta de gobierno realizada el 25 de febrero de 2016 " +
                    "queremos recordar a nuestro cliente el antes y el despues de nuestra empresa",
            color = MaterialTheme.colorScheme.surfaceDim.copy(0.7f),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Column(modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(290.dp))
        Text(
            text = "¡Presiona aquí para ver el video informativo!",
            color = Color.Blue,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=J8SBp4SyvLc"))
                context.startActivity(intent)
            }
        )

        Column(Modifier.fillMaxSize().padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,) {
            Text("Proximos juegos!",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colorScheme.surfaceDim.copy(0.7f)
            )

            Text("Durante la temporada de otono agregaremos grandes recompensas a nuestro cliente uno de estos seria nuestra obra maestra :" +
                    " ",color = MaterialTheme.colorScheme.surfaceDim.copy(1.2f),
                style = MaterialTheme.typography.bodyMedium,)
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.factoriologo),
                contentDescription = "Factorio Logo",
                modifier = Modifier.fillMaxWidth().height(100.dp).clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Fit,
                
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text("Y porsupuesto!", color = MaterialTheme.colorScheme.surface)
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.factoriospaceage),
                contentDescription = "Factorio Logo",
                modifier = Modifier.fillMaxWidth().height(100.dp).clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Fit,

                )
            Spacer(modifier = Modifier.height(10.dp))

        }

    }



    /**
     * Columna secundaria que se alinea en la parte inferior y final de la pantalla.
     * Se utiliza para colocar el botón de navegación.
     */
    Column (modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom){
        // Botón que, al ser presionado, navega de regreso a la pantalla de inicio (Home).
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.0f),
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Column(Modifier.fillMaxSize().padding(3.dp)
                , horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom) {
            Button(onClick = {
                viewModel.navigateTo(screen = Screen.Home)
            }
            ) {
                Text(text = "Volver al inicio")
            }
                //Hola

        }


        }
    }
}