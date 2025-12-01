package com.actividad_22

// In app/src/test/java/com/actividad_22/screen/LoginScreenTest.ktpackage com.actividad_22.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.actividad_22.screen.LoginScreen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    // This rule provides a test environment for Jetpack Compose UI.
    @get:Rule
    val composeTestRule = createComposeRule()

    // Mocks for dependencies that LoginScreen needs.
    // We use mockk() to create dummy versions of these classes.
    private lateinit var mockNavController: NavController
    private lateinit var mockMainViewModel: MainViewModel
    private lateinit var mockUserViewModel: UserViewModel

    @Before
    fun setUp() {
        // Initialize mocks before each test
        mockNavController = mockk(relaxed = true) // relaxed = true allows the mock to ignore calls we don't explicitly verify
        mockMainViewModel = mockk(relaxed = true)
        mockUserViewModel = mockk(relaxed = true)

        // Set the content for the test. This is where we call our LoginScreen.
        composeTestRule.setContent {
            LoginScreen(
                navController = mockNavController,
                viewModel = mockMainViewModel,
                userViewModel = mockUserViewModel
            )
        }
    }

    // Add this inside the LoginScreenTest class

    @Test
    fun loginScreen_initialState_displaysAllElements() {
        // 1. Check if the title "Bienvenido" is displayed
        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()

        // 2. Check for the email input field using its label
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()

        // 3. Check for the password input field
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // 4. Check if the "Iniciar Sesión" button is displayed and enabled
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed().assertIsEnabled()

        // 5. Check if the "Crear una" button is displayed
        composeTestRule.onNodeWithText("Crear una").assertIsDisplayed()
    }


    // Add this inside the LoginScreenTest class
// You may need to add: import io.mockk.coEvery

    @Test
    fun loginButton_whenClickedWithValidInput_callsViewModel() {
        // Arrange: Mock the behavior of userViewModel.login.
        // We don't care about the return value for this test, so we can keep it simple.
        // coEvery is used for suspend functions.
        coEvery { mockUserViewModel.login(any(), any()) } returns null // Simulate a failed login first to test the click

        // Act: Find the input fields and perform actions
        val emailField = composeTestRule.onNodeWithText("Correo electrónico")
        val passwordField = composeTestRule.onNodeWithText("Contraseña")
        val loginButton = composeTestRule.onNodeWithText("Iniciar Sesión")

        // Type text into the fields
        emailField.performTextInput("test@example.com")
        passwordField.performTextInput("password123")

        // Click the button
        loginButton.performClick()

        // Assert: Verify that the login function on the ViewModel was called
        // We use `verify` from MockK to check if the function was executed.
        verify { runBlocking { mockUserViewModel.login("test@example.com", "password123") } }
    }



}


