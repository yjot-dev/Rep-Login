package com.yjotdev.login

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.login.application.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.usecase.email.EmailUseCase
import com.yjotdev.login.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.login.domain.usecase.user.FindUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateUserUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class UiViewModelTest {

    // Dependencias Mockeadas
    private val findUserUseCase: FindUserUseCase = mockk()
    private val insertUserUseCase: InsertUserUseCase = mockk()
    private val updateUserUseCase: UpdateUserUseCase = mockk()
    private val deleteUserUseCase: DeleteUserUseCase = mockk()
    private val changePasswordUserUseCase: ChangePasswordUserUseCase = mockk()
    private val emailUseCase: EmailUseCase = mockk()

    // ViewModel bajo prueba
    private lateinit var viewModel: UiViewModel

    // Dispatcher para pruebas de corrutinas
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UiViewModel(
            findUserUseCase,
            insertUserUseCase,
            updateUserUseCase,
            deleteUserUseCase,
            changePasswordUserUseCase,
            emailUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateIsClean() = runTest {
        val state = viewModel.uiState.value
        assertNull(state.user)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun setRandomCodeUpdatesState() {
        val code = 1234
        viewModel.setRandomCode(code)
        assertEquals(code, viewModel.uiState.value.randomCode)
    }

    @Test
    fun setPasswordUpdatesState() {
        val pass = "secret"
        viewModel.setPassword(pass)
        assertEquals(pass, viewModel.uiState.value.password)
    }

    @Test
    fun cleanStateResetsUiModel() {
        // GIVEN: Estado sucio
        viewModel.setRandomCode(9999)
        viewModel.setPassword("test")

        // WHEN
        viewModel.cleanState()

        // THEN
        val state = viewModel.uiState.value
        assertEquals(0, state.randomCode) // Asumiendo que 0 es default en UiModel
        assertEquals("", state.password)  // Asumiendo string vacío default
    }

    @Test
    fun findUserSuccessUpdatesState() = runTest {
        // GIVEN
        val fakeUser = UserEntity(id = 1, name = "Test", email = "t@t.com", password = "123")
        coEvery { findUserUseCase(any()) } returns Result.Success(fakeUser)

        // WHEN
        viewModel.findUser("Test", "123")
        testDispatcher.scheduler.advanceUntilIdle() // Esperar a que la corrutina termine

        // THEN
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.wasFound)
        assertEquals(fakeUser, state.user)
        assertNull(state.error)
    }

    @Test
    fun findUserErrorUpdatesErrorMessage() = runTest {
        // GIVEN
        val errorMessage = "Usuario no encontrado"
        coEvery { findUserUseCase(any()) } returns Result.Error(Exception(errorMessage))

        // WHEN
        viewModel.findUser("Test", "123")
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.wasFound)
        assertNull(state.user)
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun insertUserSuccessSetsWasInserted() = runTest {
        // GIVEN
        coEvery { insertUserUseCase(any()) } returns Result.Success(Unit)

        // WHEN
        viewModel.insertUser("New User", "new@test.com", "pass")
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        assertTrue(viewModel.uiState.value.wasInserted)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun updateUserUsesCurrentUserId() = runTest {
        // GIVEN: Simulamos que ya buscamos un usuario y lo tenemos en el estado
        val existingUser = UserEntity(id = 55, name = "Old", email = "old@t.com", password = "old")
        coEvery { findUserUseCase(any()) } returns Result.Success(existingUser)
        viewModel.findUser("x", "x")
        testDispatcher.scheduler.advanceUntilIdle()

        // Mockeamos el update
        coEvery { updateUserUseCase(any(), any()) } returns Result.Success(Unit)

        // WHEN
        viewModel.updateUser("NewName", "new@t.com", "newpass")
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN: Verificamos que se llamó al caso de uso con el ID correcto (55)
        coVerify { updateUserUseCase(eq(55), any()) }
        assertTrue(viewModel.uiState.value.wasUpdated)
    }

    @Test
    fun deleteUserSuccessSetsWasDeleted() = runTest {
        // GIVEN: Simulamos usuario en estado
        val existingUser = UserEntity(id = 10, name = "Del", email = "d@d.com", password = "d")
        coEvery { findUserUseCase(any()) } returns Result.Success(existingUser)
        viewModel.findUser("x", "x")
        testDispatcher.scheduler.advanceUntilIdle()

        coEvery { deleteUserUseCase(10) } returns Result.Success(Unit)

        // WHEN
        viewModel.deleteUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        assertTrue(viewModel.uiState.value.wasDeleted)
    }

    @Test
    fun sendEmailGeneratesCodeAndCallsUseCase() = runTest {
        // GIVEN
        coEvery { emailUseCase(any()) } returns Result.Success(Unit)

        // WHEN
        viewModel.sendEmail("test@email.com")
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assertTrue(state.wasEmailed)
        assertNotEquals(0, state.randomCode) // Verifica que se generó un código aleatorio

        // Verificamos que se llamó al caso de uso con el código incluido en el texto
        coVerify {
            emailUseCase(match { emailEntity ->
                emailEntity.text.contains(state.randomCode.toString())
            })
        }
    }

    @Test
    fun recoveryPasswordSuccessWhenCodeMatches() = runTest {
        // GIVEN: Establecemos un código aleatorio previo en el estado
        val code = 5678
        viewModel.setRandomCode(code)

        coEvery { changePasswordUserUseCase(any()) } returns Result.Success(Unit)

        // WHEN: Llamamos con el mismo código (en string)
        viewModel.recoveryPassword("5678", "mail@t.com", "newPass")
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        assertTrue(viewModel.uiState.value.wasUpdated) // recovery llama a changePasswordUser que setea wasUpdated
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun recoveryPasswordFailsWhenCodeDoesNotMatch() = runTest {
        // GIVEN: Código en estado
        viewModel.setRandomCode(1111)

        // WHEN: Llamamos con código diferente
        viewModel.recoveryPassword("9999", "mail@t.com", "newPass")

        // THEN
        assertEquals("Codigo incorrecto", viewModel.uiState.value.error)

        // Verificamos que NO se llamó al caso de uso de cambiar contraseña
        coVerify(exactly = 0) { changePasswordUserUseCase(any()) }
    }

    @Test
    fun clearFlagsResetsBooleans() {
        // GIVEN: Estado con flags activas
        coEvery { insertUserUseCase(any()) } returns Result.Success(Unit)
        viewModel.insertUser("a","b","c")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.wasInserted)

        // WHEN
        viewModel.clearFlags()

        // THEN
        val state = viewModel.uiState.value
        assertFalse(state.wasInserted)
        assertFalse(state.wasFound)
        assertFalse(state.wasUpdated)
        assertFalse(state.wasDeleted)
        assertFalse(state.wasEmailed)
        assertNull(state.error)
    }
}
