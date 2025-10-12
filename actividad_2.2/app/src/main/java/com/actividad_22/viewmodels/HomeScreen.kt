package com.actividad_22.viewmodels

import androidx.*
import androidx.annotation.experimental.Experimental
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.navigation.Screen
import kotlinx.coroutines.launch


@OptIn( ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(
    navController:NavController,
    viewModel: MainViewModel= viewModel ()
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope ()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent =  {
        ModalDrawerSheet {
            Text( text = "Menu ", modifier = Modifier.padding(all = 16.dp))
            NavigationDrawerItem(
                label = {Text(text = "Perfil")},
                selected = false,
                onClick = {scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.Profile)
                }

            )
            NavigationDrawerItem(
                label = {Text(text = "Tienda")},
                selected = false,
                onClick = {scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.Store)}
            )
            NavigationDrawerItem(
                label = {Text(text = "Nosotros" )},
                selected = false,
                onClick = {scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.Us)}

            )
            NavigationDrawerItem(
                label = {Text(text = "Eventos")},
                selected = false,
                onClick = {scope.launch {  drawerState.close()}
                viewModel.navigateTo(Screen.Event)}
            )

        }
    }
    ){
        Scaffold (
            topBar = {
                TopAppBar(
                    title = {Text(text = "Pantalla Home")},
                    navigationIcon={
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ){ innerPadding ->
            Column (modifier = Modifier.padding(innerPadding).fillMaxSize()
            , horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(text = "Bienvenidoa a la pagina de inicio MvvM")
                Spacer(modifier = Modifier.height(height = 16.dp))
                Button(onClick ={viewModel.navigateTo(Screen.Settings)} ) {Text(text = "Ir a configuracion") }
            }
        }
}
}