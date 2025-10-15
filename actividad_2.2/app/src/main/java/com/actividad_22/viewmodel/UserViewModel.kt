package com.actividad_22.viewmodel

import androidx.lifecycle.ViewModel
import com.actividad_22.navigation.UserUiState
import com.actividad_22.navigation.UsuarioError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UserViewModel : ViewModel() {
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