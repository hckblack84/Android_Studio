package com.actividad_22.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel
import android.widget.Toast
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()
) {
    // val estado by viewModel.estado.collectAsState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    val clientList by userViewModel.allClients.collectAsState(initial = emptyList())

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Iniciar Sesión")

            EmailInput(emailState = email)
            PasswordInput(passwordState = password)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutine.launch {
                        if (userViewModel.userExist(email.value, password.value)){
                            navController.navigate(route = Screen.Home.route)
                        }else{
                            Toast.makeText(context, "Cuenta no encontrada", Toast.LENGTH_SHORT).show()
                        }
                    }

                    /*clientList.forEach { client ->
                        println("email: >${client.email_client}< / >${email.value}<")
                        println("password: >${client.password_client}< / >${password.value}<")
                        println(client.email_client == email.value)
                        println(client.password_client == password.value)
                        if (client.email_client == email.value && client.password_client == password.value){
                            navController.navigate(route = Screen.Home.route)
                        }
                    }*/

            }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Iniciar Sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { viewModel.navigateTo(Screen.Register) }) {
                Text(text = "¿No tienes cuenta? Crear una")
            }
        }



    }
    /*
    LazyColumn {
        items(clientList) { client ->
            Text(text = "id: ${client.id_client}")
            Text(text = "name: ${client.name_client}")
            Text(text = "email: ${client.email_client}")
            Text(text = "password: ${client.password_client}")
            Text(text = "direction: ${client.direction_client}")
        }
    }*/
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation
    )
}

@Composable
fun EmailInput(emailState: MutableState<String>, labelId: String = "Email") {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun PasswordInput(passwordState: MutableState<String>, labelId: String = "Contraseña") {
    InputField(
        valueState = passwordState,
        labelId = labelId,
        keyboardType = KeyboardType.Password,
        visualTransformation = PasswordVisualTransformation()    )
}