package com.actividad_22

import com.actividad_22.data.local.Client
import com.actividad_22.data.repository.ClientRepository
import com.actividad_22.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

/**
 * Esta clase se encarga de probar la lógica de `UserViewModel`.
 * Las pruebas verifican que el manejo del estado del formulario, el inicio de sesión,
 * el cierre de sesión y otras funcionalidades se comporten como se espera.
 *
 * Usamos `ExperimentalCoroutinesApi` para poder probar código que usa corutinas (tareas en segundo plano).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    // Un despachador de pruebas para controlar cómo se ejecutan las tareas en segundo plano (corutinas) durante las pruebas.
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    // Una versión "simulada" (mock) de nuestro repositorio de clientes. Nos permite fingir cómo se comporta la base de datos.
    private lateinit var mockRepository: ClientRepository
    // La instancia del ViewModel que vamos a probar.
    private lateinit var userViewModel: UserViewModel

    // Un cliente de ejemplo que usaremos en varias pruebas.
    private val testClient = Client(
        id_client = 1L,
        name_client = "Test User",
        email_client = "test@user.com",
        password_client = "pass1234",
        direction_client = "Direccion 123"
    )

    /**
     * Esta función se ejecuta antes de cada prueba.
     * Prepara el entorno necesario para que las pruebas funcionen correctamente.
     */
    @Before
    fun setUp() {

        // Le decimos a las corutinas que usen nuestro despachador de pruebas.
        Dispatchers.setMain(testDispatcher)

        // Creamos el repositorio simulado. `relaxed = true` significa que no fallará si se llama a una función no definida.
        mockRepository = mockk(relaxed = true)

        // Creamos el ViewModel con el repositorio simulado.
        userViewModel = UserViewModel(mockRepository)

        // Limpiamos todos los campos del formulario antes de cada prueba.
        userViewModel.onNombreChange("")
        userViewModel.onCorreoChange("")
        userViewModel.onClaveChange("")
        userViewModel.onDirreccionChange("")
        userViewModel.onAceptaTerminosChange(false)
    }

    /**
     * Esta función se ejecuta después de cada prueba.
     * Limpia los cambios realizados durante la preparación para no afectar a otras pruebas.
     */
    @After
    fun tearDown() {
        // Restaura el despachador de corutinas original.
        Dispatchers.resetMain()
    }



    /**
     * Prueba que el formulario se considera válido cuando todos los datos son correctos.
     */
    @Test
    fun validarFormulario_conDatosValidos_devuelveTrue() = runTest(testDispatcher) {
        // Llenamos el formulario con información válida.
        userViewModel.onNombreChange("LuisA")
        userViewModel.onCorreoChange("prueba@valida.com")
        userViewModel.onClaveChange("12345678") //
        userViewModel.onDirreccionChange("Calle Falsa 123")
        userViewModel.onAceptaTerminosChange(true)

        val esValido = userViewModel.validarFormulario()
        val errores = userViewModel.estado.value.errores

        // Verificamos que el resultado es válido y que no hay mensajes de error.
        assertTrue(esValido, "El formulario debería ser válido.")
        assertNull(errores.nombre)
        assertNull(errores.correo)
        assertNull(errores.clave)
        assertNull(errores.direccion)
        assertNull(errores.aceptaTerminos)
    }

    /**
     * Prueba que el formulario se considera inválido si el nombre es muy corto.
     */
    @Test
    fun validarFormulario_conNombreDemasiadoCorto_devuelveFalse() = runTest(testDispatcher) {
        // Llenamos el formulario con un nombre demasiado corto.
        userViewModel.onNombreChange("ABC")
        userViewModel.onCorreoChange("a@a.com")
        userViewModel.onClaveChange("123456")
        userViewModel.onDirreccionChange("Dir")
        userViewModel.onAceptaTerminosChange(true)

        val esValido = userViewModel.validarFormulario()
        val errorNombre = userViewModel.estado.value.errores.nombre

        // Verificamos que el resultado es inválido y que hay un mensaje de error para el nombre.
        assertFalse(esValido, "El formulario debería ser inválido por nombre corto.")
        assertNotNull(errorNombre)
        assertEquals("Porfavor ingrese un nombre entre 4 y 10 caracteres", errorNombre)
    }

    /**
     * Prueba que el formulario se considera inválido si el correo electrónico no tiene un formato correcto.
     */
    @Test
    fun validarFormulario_conCorreoInvalido_devuelveFalse() = runTest(testDispatcher) {
        // Llenamos el formulario con un correo no válido.
        userViewModel.onNombreChange("NombreValido")
        userViewModel.onCorreoChange("correo.invalido")
        userViewModel.onClaveChange("123456")
        userViewModel.onDirreccionChange("Dir")
        userViewModel.onAceptaTerminosChange(true)

        val esValido = userViewModel.validarFormulario()
        val errorCorreo = userViewModel.estado.value.errores.correo

        // Verificamos que el resultado es inválido y que hay un mensaje de error para el correo.
        assertFalse(esValido, "El formulario debería ser inválido por correo.")
        assertNotNull(errorCorreo)
        assertEquals("Correo invalido", errorCorreo)
    }

    /**
     * Prueba que el formulario se considera inválido si no se aceptan los términos y condiciones.
     */
    @Test
    fun validarFormulario_conTerminosNoAceptados_devuelveFalse() = runTest(testDispatcher) {
        // Llenamos el formulario pero no marcamos la casilla de términos.
        userViewModel.onNombreChange("Valid Name")
        userViewModel.onCorreoChange("valid@mail.com")
        userViewModel.onClaveChange("123456")
        userViewModel.onDirreccionChange("Valid Direction")
        userViewModel.onAceptaTerminosChange(false) // <--- Falla aquí

        val esValido = userViewModel.validarFormulario()
        val errorTerminos = userViewModel.estado.value.errores.aceptaTerminos
        assertFalse(esValido, "El formulario debería ser inválido por términos no aceptados.")
        // Verificamos que hay un mensaje de error para los términos y condiciones.
        assertNotNull(errorTerminos)
        assertEquals("Debe aceptar los terminos y condiciones", errorTerminos)
    }



    /**
     * Prueba que el inicio de sesión funciona cuando se usan las credenciales correctas.
     */
    @Test
    fun login_conCredencialesCorrectas_devuelveClienteYActualizaEstado() = runTest(testDispatcher) {
        // Le decimos a nuestro repositorio simulado que cuando se llame a `login`, devuelva nuestro cliente de prueba.
        coEvery { mockRepository.login(any(), any()) } returns testClient

        // Intentamos iniciar sesión con las credenciales del cliente de prueba.
        val resultClient = userViewModel.login(testClient.email_client, testClient.password_client)

        // Verificamos que la función devolvió el cliente correcto y que el estado del ViewModel se actualizó.
        assertEquals(testClient, resultClient)
        assertEquals(testClient, userViewModel.currentClient.value)
    }

    /**
     * Prueba que el inicio de sesión falla cuando se usan credenciales incorrectas.
     */
    @Test
    fun login_conCredencialesIncorrectas_devuelveNullYNoActualizaEstado() = runTest(testDispatcher) {
        // Le decimos al repositorio simulado que `login` debe devolver `null` (como si el usuario no existiera).
        coEvery { mockRepository.login(any(), any()) } returns null

        // Intentamos iniciar sesión con credenciales incorrectas.
        val resultClient = userViewModel.login("wrong@email.com", "wrongpass")

        // Verificamos que la función devolvió `null` y que el estado del ViewModel no cambió.
        assertNull(resultClient)
        assertNull(userViewModel.currentClient.value)
    }



    /**
     * Prueba que la función de cierre de sesión (`logout`) limpia la información del cliente actual.
     */
    @Test
    fun logout_limpiaElClienteActual() = runTest(testDispatcher) {
        // Primero, simulamos que un cliente ha iniciado sesión.
        userViewModel._currentClient.value = testClient

        // Llamamos a la función para cerrar sesión.
        userViewModel.logout()

        // Verificamos que la información del cliente se ha borrado.
        assertNull(userViewModel.currentClient.value)
    }

    /**
     * Prueba que al cambiar el nombre en el formulario, el estado del ViewModel se actualiza correctamente.
     */
    @Test
    fun onNombreChange_actualizaElEstadoDelFormulario() {
        val nuevoNombre = "Practicando Tests"

        // Cambiamos el valor del campo de nombre.
        userViewModel.onNombreChange(nuevoNombre)

        // Verificamos que el estado se actualizó con el nuevo nombre y que no hay errores asociados.
        assertEquals(nuevoNombre, userViewModel.estado.value.nombre)
        assertNull(userViewModel.estado.value.errores.nombre)
    }
}