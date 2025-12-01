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
import com.actividad_22.tools.EmailVerified
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {


    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private lateinit var mockRepository: ClientRepository
    private lateinit var userViewModel: UserViewModel

    private val testClient = Client(
        id_client = 1L,
        name_client = "Test User",
        email_client = "test@user.com",
        password_client = "pass1234",
        direction_client = "Direccion 123"
    )

    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)

        mockRepository = mockk(relaxed = true)

        userViewModel = UserViewModel(mockRepository)

        userViewModel.onNombreChange("")
        userViewModel.onCorreoChange("")
        userViewModel.onClaveChange("")
        userViewModel.onDirreccionChange("")
        userViewModel.onAceptaTerminosChange(false)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    @Test
    fun validarFormulario_conDatosValidos_devuelveTrue() = runTest(testDispatcher) {
        userViewModel.onNombreChange("LuisA")
        userViewModel.onCorreoChange("prueba@valida.com")
        userViewModel.onClaveChange("12345678") //
        userViewModel.onDirreccionChange("Calle Falsa 123")
        userViewModel.onAceptaTerminosChange(true)

        val esValido = userViewModel.validarFormulario()
        val errores = userViewModel.estado.value.errores

        assertTrue(esValido, "El formulario debería ser válido.")
        assertNull(errores.nombre)
        assertNull(errores.correo)
        assertNull(errores.clave)
        assertNull(errores.direccion)
        assertNull(errores.aceptaTerminos)
    }

    @Test
    fun validarFormulario_conNombreDemasiadoCorto_devuelveFalse() = runTest(testDispatcher) {
        userViewModel.onNombreChange("ABC")
        userViewModel.onCorreoChange("a@a.com")
        userViewModel.onClaveChange("123456")
        userViewModel.onDirreccionChange("Dir")
        userViewModel.onAceptaTerminosChange(true)

        val esValido = userViewModel.validarFormulario()
        val errorNombre = userViewModel.estado.value.errores.nombre

        assertFalse(esValido, "El formulario debería ser inválido por nombre corto.")
        assertNotNull(errorNombre)
        assertEquals("Porfavor ingrese un nombre entre 4 y 10 caracteres", errorNombre)
    }

    @Test
    fun validarFormulario_conCorreoInvalido_devuelveFalse() = runTest(testDispatcher) {
        userViewModel.onNombreChange("NombreValido")
        userViewModel.onCorreoChange("correo.invalido")
        userViewModel.onClaveChange("123456")
        userViewModel.onDirreccionChange("Dir")
        userViewModel.onAceptaTerminosChange(true)

        val esValido = userViewModel.validarFormulario()
        val errorCorreo = userViewModel.estado.value.errores.correo

        assertFalse(esValido, "El formulario debería ser inválido por correo.")
        assertNotNull(errorCorreo)
        assertEquals("Correo invalido", errorCorreo)
    }

    @Test
    fun validarFormulario_conTerminosNoAceptados_devuelveFalse() = runTest(testDispatcher) {
        userViewModel.onNombreChange("Valid Name")
        userViewModel.onCorreoChange("valid@mail.com")
        userViewModel.onClaveChange("123456")
        userViewModel.onDirreccionChange("Valid Direction")
        userViewModel.onAceptaTerminosChange(false) // <--- Falla aquí

        val esValido = userViewModel.validarFormulario()
        val errorTerminos = userViewModel.estado.value.errores.aceptaTerminos
        assertFalse(esValido, "El formulario debería ser inválido por términos no aceptados.")
        assertNotNull(errorTerminos)
        assertEquals("Debe aceptar los terminos y condiciones", errorTerminos)
    }



    @Test
    fun login_conCredencialesCorrectas_devuelveClienteYActualizaEstado() = runTest(testDispatcher) {
        coEvery { mockRepository.login(any(), any()) } returns testClient

        val resultClient = userViewModel.login(testClient.email_client, testClient.password_client)

        assertEquals(testClient, resultClient)
        assertEquals(testClient, userViewModel.currentClient.value)
    }

    @Test
    fun login_conCredencialesIncorrectas_devuelveNullYNoActualizaEstado() = runTest(testDispatcher) {
        coEvery { mockRepository.login(any(), any()) } returns null

        val resultClient = userViewModel.login("wrong@email.com", "wrongpass")

        assertNull(resultClient)
        assertNull(userViewModel.currentClient.value)
    }



    @Test
    fun logout_limpiaElClienteActual() = runTest(testDispatcher) {
        userViewModel._currentClient.value = testClient

        userViewModel.logout()

        assertNull(userViewModel.currentClient.value)
    }

    @Test
    fun onNombreChange_actualizaElEstadoDelFormulario() {
        val nuevoNombre = "Practicando Tests"

        userViewModel.onNombreChange(nuevoNombre)

        assertEquals(nuevoNombre, userViewModel.estado.value.nombre)
        assertNull(userViewModel.estado.value.errores.nombre)
    }
}