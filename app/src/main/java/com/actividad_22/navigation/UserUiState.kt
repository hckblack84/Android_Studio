package com.actividad_22.navigation

/**

 * esta data class representa el estado actual del formulario de registro de usuario en la UI.
 * almacena los valores ingresados por el usuario para campos como el nombre, el correo electrónico,
 * etc
 * permitiendo una correcta validacion

 */
data class UserUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: UsuarioError = UsuarioError()
)

data class UsuarioError(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null,
    val aceptaTerminos: String? = null
)