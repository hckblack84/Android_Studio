package com.actividad_22.viewmodels

import androidx.lifecycle.ViewModel
import com.actividad_22.navigation.NavigationEvent
import com.actividad_22.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()

    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()


    fun navigateTo(screen: Screen){
        CoroutineScope(context = Dispatchers.Main).launch{
            _navigationEvents.emit(value = NavigationEvent.Navigateto(route = screen))
        }
    }
    fun navigateBack(){
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.popBackStack)
        }
    }

    fun navigateUp(){
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.NavigateUp)
        }
    }
}