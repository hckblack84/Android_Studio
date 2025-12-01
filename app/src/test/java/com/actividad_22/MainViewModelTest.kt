package com.actividad_22

import com.actividad_22.navigation.NavigationEvent
import com.actividad_22.navigation.Screen
import com.actividad_22.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenNavigationTest {

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private suspend fun getEmittedEvent(scope: TestScope): NavigationEvent {
        return suspendCoroutine { continuation ->
            scope.backgroundScope.launch {
                try {
                    val event = mainViewModel.navigationEvents.first()
                    continuation.resume(event)
                } catch (e: Exception) {
                    println("Error al recolectar evento de navegación: ${e.message}")
                }
            }
        }
    }

    @Test
    fun navigateToHomeScreen_emiteEventoCorrecto() = runTest(testDispatcher) {
        val homeScreen = Screen.Home

        val deferredEvent = backgroundScope.launch {
            getEmittedEvent(this@runTest)
        }

    }

}