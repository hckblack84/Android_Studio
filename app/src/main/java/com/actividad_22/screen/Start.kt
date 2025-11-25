package com.actividad_22.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.actividad_22.R
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel

private val DeepDarkBackground = Color(0xFF0F1218)
private val AccentColor = Color(0xFFF38A1D)

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    Box(
        modifier =
            Modifier.fillMaxSize()
                .background(DeepDarkBackground)
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentColor.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = RoundedCornerShape(60.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.control),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))


            // Título
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)) {
                    append("Bienvenido a\n")
                }
                withStyle(style = SpanStyle(color = AccentColor, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)) {
                    append("Level Up Gaming")
                }
            }

            ClickableText(
                text = annotatedText,
                onClick = {}
            )


            // Espaciador para separar el título de los botones
            Spacer(modifier = Modifier.height(80.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Login)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentColor
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Iniciar Sesion")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Register)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentColor
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Registrarse")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Home)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentColor
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Acceder como invitado")
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
