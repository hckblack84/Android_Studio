package com.actividad_22.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.util.copy
import com.actividad_22.data.local.Client
import com.actividad_22.data.repository.ClientRepository
import com.actividad_22.navigation.UserUiState
import com.actividad_22.navigation.UsuarioError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val clientRepository: ClientRepository) : ViewModel() {

    val allClients = clientRepository.allClients;

    fun insertClient(client: Client) = viewModelScope.launch{
        clientRepository.insertClient(client)
    }

    fun deleteClient(client: Client) = viewModelScope.launch{
        clientRepository.deleteClient(client)
    }

    fun getClient(email:String, password:String): List<Client>{
        return clientRepository.getClient(email, password)
    }

    class ClientViewModelFactory(private val clientRepository: ClientRepository) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(clientRepository) as T
            }
            throw IllegalArgumentException("Ni modo flaco no esta la vista del modelo")
        }
    }


    private val _estado = MutableStateFlow(UserUiState())
val estado : StateFlow<UserUiState> =_estado


    fun onNombreChange(valor: String){
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }
    fun onCorreoChange(valor: String){
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }
    fun onClaveChange(valor: String){
_estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }
    fun onDirreccionChange(valor: String){
_estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }
fun onAceptaTerminosChange(valor: Boolean){
    _estado.update { it.copy(aceptaTerminos = valor) }
}

    fun validarFormulario(): Boolean{

        val estadoActual = _estado.value
        val errores = UsuarioError(
            nombre = if (estadoActual.nombre.isBlank()) "Porfavor ingrese un nombre" else null,
            correo = if (estadoActual.correo.isBlank()) "Porfavor ingrese un correo" else null,
            clave = if (estadoActual.clave.isBlank()) "Porfavor ingrese una clave" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Porfavor ingrese una direccion" else null
        )
        val cantErrores= listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }



        return !cantErrores
    }


    }