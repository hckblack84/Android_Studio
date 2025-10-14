package com.actividad_22.viewmodels
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
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.actividad_22.navigation.Screen



@Composable
fun EventScreen(
    navController: NavController,
    viewModel: MainViewModel){
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Text(text = "¡Eventos Proximos!",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
            )
        Spacer(modifier= Modifier.height(20.dp) )
        Text(
            text = "Proximo Evento a las afueras de la casa de marquitos " +
                    "habra juegos de azar y mujerzuelas "
        )
    }



    Column (modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom){
    Button(onClick = {viewModel.navigateTo(screen = Screen.Home)}){Text(text = "Volver al inicio")}
}
}