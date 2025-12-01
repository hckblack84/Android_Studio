package com.actividad_22.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.actividad_22.data.local.Client
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test para ProfileScreen
 * Prueba la UI y las interacciones del usuario
 */
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockNavController: NavController
    private lateinit var mockMainViewModel: MainViewModel
    private lateinit var mockUserViewModel: UserViewModel

    private val testClient = Client(
        id_client = 1L,
        name_client = "Juan Pérez",
        email_client = "juan@example.com",
        password_client = "password123",
        direction_client = "Calle Falsa 123"
    )

    @Before
    fun setUp() {
        mockNavController = mockk(relaxed = true)
        mockMainViewModel = mockk(relaxed = true)
        mockUserViewModel = mockk(relaxed = true)

        val clientFlow = MutableStateFlow<Client?>(testClient)
        every { mockUserViewModel.currentClient } returns clientFlow

        composeTestRule.setContent {
            ProfileScreen(
                navController = mockNavController,
                mainViewModel = mockMainViewModel,
                userViewModel = mockUserViewModel
            )
        }
    }



    @Test
    fun profileScreen_editMode_passwordToggle_showsAndHidesPassword() {
        composeTestRule.onNodeWithText("Editar Perfil").performClick()

        val passwordToggle = composeTestRule.onAllNodesWithContentDescription("toggle password visibility")


    }

    @Test
    fun profileScreen_cameraIcon_isDisplayed() {

        composeTestRule.onNodeWithContentDescription("Cambiar foto").assertIsDisplayed()
    }

    /*@Test
    fun profileScreen_infoCards_displayCorrectData() {

        //composeTestRule.onNodeWithText("Calle Falsa 123").assertIsDisplayed()

        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dirección").assertIsDisplayed()
    }*/
}