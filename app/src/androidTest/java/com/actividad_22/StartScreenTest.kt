package com.actividad_22

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import com.actividad_22.navigation.Screen
import com.actividad_22.screen.StartScreen
import com.actividad_22.viewmodel.MainViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class StartScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun startScreenAppears() {
        composeRule.setContent {
            StartScreen(
                navController = TestNavHostController(composeRule.activity),
                viewModel = FakeViewModel()
            )
        }

        composeRule.onNodeWithText("Iniciar Sesion").assertExists()
        composeRule.onNodeWithText("Registrarse").assertExists()
        composeRule.onNodeWithText("Acceder como invitado").assertExists()
        composeRule.onNodeWithText("Bienvenido a\nLevel Up Gaming").assertExists()
    }

    @Test
    fun navigateToLoginEvent() {
        val fakeVM = FakeViewModel()

        composeRule.setContent {
            StartScreen(
                navController = TestNavHostController(composeRule.activity),
                viewModel = fakeVM
            )
        }

        composeRule.onNodeWithText("Iniciar Sesion").performClick()
        assertEquals(Screen.Login, fakeVM.lastRoute)

        composeRule.onNodeWithText("Registrarse").performClick()
        assertEquals(Screen.Register, fakeVM.lastRoute)
    }
}

class FakeViewModel : MainViewModel() {
    var lastRoute: Screen? = null

    override fun navigateTo(route: Screen) {
        lastRoute = route
    }
}
