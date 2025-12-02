package com.actividad_22.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.actividad_22.data.local.Client
import com.actividad_22.data.repository.ClientRepository
import com.actividad_22.navigation.UserUiState
import com.actividad_22.navigation.UsuarioError
import com.actividad_22.tools.EmailVerified
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Gestiona los datos y la lógica relacionados con los usuarios (clientes) para la interfaz de la aplicación.
 * Se comunica con la base de datos a través de `clientRepository`.
 *
 * @param clientRepository El repositorio para acceder a los datos de los clientes.
 */
open class UserViewModel(private val clientRepository: ClientRepository) : ViewModel() {

    // Herramienta para verificar si un correo electrónico tiene un formato válido.
    val emailVerified: EmailVerified = EmailVerified()
    // Una lista que se actualiza automáticamente con todos los clientes de la base de datos.
    val allClients = clientRepository.allClients

    // Guarda la información del cliente que ha iniciado sesión. Es `null` si nadie ha iniciado sesión.
    val _currentClient = MutableStateFlow<Client?>(null)
    // Versión pública y de solo lectura de `_currentClient` para que la interfaz la observe.
    val currentClient: StateFlow<Client?> = _currentClient

    // Guarda el estado actual de los campos de un formulario (ej: registro de usuario).
    private val _estado = MutableStateFlow(UserUiState())
    // Versión pública y de solo lectura de `_estado` para que la interfaz la observe.
    val estado: StateFlow<UserUiState> = _estado

    /**
     * Guarda un nuevo cliente en la base de datos.
     * @param client El cliente a guardar.
     */
    fun insertClient(client: Client) = viewModelScope.launch {
        clientRepository.insertClient(client)
    }

    /**
     * Elimina un cliente de la base de datos.
     * @param client El cliente a eliminar.
     */
    fun deleteClient(client: Client) = viewModelScope.launch {
        clientRepository.deleteClient(client)
    }

    /**
     * Borra todos los clientes de la tabla en la base de datos.
     */
    fun truncateClients() = viewModelScope.launch {
        clientRepository.clearClientTable()
    }

    // --- FUNCIONES PARA MANEJAR LA SESIÓN DEL USUARIO ---

    /**
     * Intenta iniciar sesión con un correo y contraseña.
     * Si tiene éxito, actualiza el `_currentClient` y devuelve los datos del cliente.
     * @param email El correo del usuario.
     * @param password La contraseña del usuario.
     * @return El objeto `Client` si el inicio de sesión es exitoso, o `null` si no lo es.
     */
    open suspend fun login(email: String, password: String): Client? {
        val client = clientRepository.login(email, password)
        _currentClient.value = client
        return client
    }

    /**
     * Carga los datos de un cliente usando su ID y lo establece como el usuario actual.
     * Útil para cuando la app se reinicia y ya había una sesión guardada.
     * @param userId El ID del cliente a cargar.
     */
    fun loadClientById(userId: Long) = viewModelScope.launch {
        val client = clientRepository.getClientById(userId)
        _currentClient.value = client
    }

    /**
     * Actualiza los datos del cliente que ha iniciado sesión.
     * @param client El objeto cliente con la información actualizada.
     */
    fun updateCurrentClient(client: Client) = viewModelScope.launch {
        clientRepository.updateClient(client)
        _currentClient.value = client
    }

    /**
     * Cierra la sesión del usuario actual, limpiando sus datos.
     */
    fun logout() {
        _currentClient.value = null
    }

    // --- FUNCIONES PARA MANEJAR EL FORMULARIO DE REGISTRO/EDICIÓN ---

    /** Actualiza el nombre en el estado del formulario y borra cualquier error asociado. */
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    /** Actualiza el correo en el estado del formulario y borra cualquier error asociado. */
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    /** Actualiza la clave (contraseña) en el estado del formulario y borra cualquier error asociado. */
    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    /** Actualiza la dirección en el estado del formulario y borra cualquier error asociado. */
    fun onDirreccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    /** Actualiza si el usuario aceptó o no los términos y condiciones. */
    fun onAceptaTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    /**
     * Comprueba que todos los campos del formulario sean válidos.
     * Actualiza el estado con los mensajes de error correspondientes si los hay.
     * @return `true` si el formulario es válido, `false` si hay errores.
     */
    fun validarFormulario(): Boolean {

        val estadoActual = _estado.value
        print(estadoActual.correo)
        // Revisa cada campo y asigna un mensaje de error si no es válido.
        val errores = UsuarioError(
            nombre = if (estadoActual.nombre.trim().length in 4..50)
                null else "Porfavor ingrese un nombre entre 4 y 10 caracteres",
            correo = if (emailVerified.isValidEmail(estadoActual.correo.trim()))
                null else "Correo invalido",
            clave = if (estadoActual.clave.length in 4..8)
                null else "Ingrese una contraseña entre 4 y 8 caracteres",
            direccion = if (estadoActual.direccion.isBlank())
                "Porfavor ingrese una direccion" else null,
            aceptaTerminos = if (estadoActual.aceptaTerminos) null else "Debe aceptar los terminos y condiciones"
        )
        // Comprueba si la lista de errores tiene algún mensaje.
        val cantErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion,
            errores.aceptaTerminos
        ).isNotEmpty()

        // Actualiza el estado del formulario con los nuevos errores (o sin ellos).
        _estado.update { it.copy(errores = errores) }

        // Devuelve `true` si no hay errores.
        return !cantErrores
    }

    /**
     * Verifica si ya existe un usuario con el mismo correo y contraseña en la base de datos.
     * @param email Correo a verificar.
     * @param password Contraseña a verificar.
     * @return `true` si el usuario existe, `false` si no.
     */
    suspend fun userExist(email: String, password: String): Boolean {
        val clients = allClients.first()
        return clients.any { client ->
            client.email_client == email && client.password_client == password
        }
    }

    /**
     * Una "fábrica" que sabe cómo crear una instancia de `UserViewModel`.
     * Es necesaria para poder pasar el `clientRepository` al crear el ViewModel.
     */
    class ClientViewModelFactory(private val clientRepository: ClientRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(clientRepository) as T
            }
            throw IllegalArgumentException("Ni modo flaco no esta la vista del modelo")
        }
    }
}
