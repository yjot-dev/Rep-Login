package com.yjotdev.login

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserRepository
import com.yjotdev.login.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.login.domain.usecase.user.FindUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateUserUseCase

/**
 * Pruebas unitarias para los casos de uso CRUD de Usuarios.
 */
class UserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var findUserUseCase: FindUserUseCase
    private lateinit var insertUserUseCase: InsertUserUseCase
    private lateinit var updateUserUseCase: UpdateUserUseCase
    private lateinit var changePasswordUserUseCase: ChangePasswordUserUseCase
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        findUserUseCase = FindUserUseCase(userRepository)
        insertUserUseCase = InsertUserUseCase(userRepository)
        updateUserUseCase = UpdateUserUseCase(userRepository)
        changePasswordUserUseCase = ChangePasswordUserUseCase(userRepository)
        deleteUserUseCase = DeleteUserUseCase(userRepository)
    }

    @Test
    fun findUserUseCaseReturnsSuccessWhenUserIsFound() = runTest {
        // Given
        val loginModel = LoginModel("testuser", "password")
        val fakeUser = UserModel(id = 1, name = "Test User", email = "test@example.com", password = "password")
        coEvery { userRepository.findUser(loginModel) } returns Result.Success(fakeUser)

        // When
        val result = findUserUseCase(loginModel)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakeUser, (result as Result.Success).data)
        coVerify(exactly = 1) { userRepository.findUser(loginModel) }
    }

    @Test
    fun findUserUseCaseReturnsErrorWhenUserNotFound() = runTest {
        // Given
        val loginModel = LoginModel("testuser", "password")
        val exception = Exception("Error al buscar usuario")
        coEvery { userRepository.findUser(loginModel) } returns Result.Error(exception)

        // When
        val result = findUserUseCase(loginModel)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userRepository.findUser(loginModel) }
    }

    @Test
    fun insertUserUseCaseReturnsSuccessWhenUserIsInserted() = runTest {
        // Given
        val newUser = UserModel(name = "New User", email = "new@example.com", password = "newpassword")
        coEvery { userRepository.insertUser(newUser) } returns Result.Success(Unit)

        // When
        val result = insertUserUseCase(newUser)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userRepository.insertUser(newUser) }
    }

    @Test
    fun insertUserUseCaseReturnsErrorWhenInsertionFails() = runTest {
        // Given
        val newUser = UserModel(name = "New User", email = "new@example.com", password = "newpassword")
        val exception = Exception("Error al insertar usuario")
        coEvery { userRepository.insertUser(newUser) } returns Result.Error(exception)

        // When
        val result = insertUserUseCase(newUser)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userRepository.insertUser(newUser) }
    }

    @Test
    fun updateUserUseCaseReturnsSuccessWhenUserIsUpdated() = runTest {
        // Given
        val userId = 1
        val userToUpdate = UserModel(name = "Updated User", email = "updated@example.com", password = "updatedpassword")
        coEvery { userRepository.updateUser(userId, userToUpdate) } returns Result.Success(Unit)

        // When
        val result = updateUserUseCase(userId, userToUpdate)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userRepository.updateUser(userId, userToUpdate) }
    }

    @Test
    fun updateUserUseCaseReturnsErrorWhenUpdateFails() = runTest {
        // Given
        val userId = 1
        val userToUpdate = UserModel(name = "Updated User", email = "updated@example.com", password = "updatedpassword")
        val exception = Exception("Error al actualizar usuario")
        coEvery { userRepository.updateUser(userId, userToUpdate) } returns Result.Error(exception)

        // When
        val result = updateUserUseCase(userId, userToUpdate)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userRepository.updateUser(userId, userToUpdate) }
    }

    @Test
    fun changePasswordUserUseCaseReturnsSuccessWhenPasswordIsChanged() = runTest {
        // Given
        val recoveryModel = RecoveryModel(email = "test@example.com", password = "newpassword")
        coEvery { userRepository.changePasswordUser(recoveryModel) } returns Result.Success(Unit)

        // When
        val result = changePasswordUserUseCase(recoveryModel)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userRepository.changePasswordUser(recoveryModel) }
    }

    @Test
    fun changePasswordUserUseCaseReturnsErrorWhenChangeFails() = runTest {
        // Given
        val recoveryModel = RecoveryModel(email = "test@example.com", password = "newpassword")
        val exception = Exception("Error al cambiar contraseña")
        coEvery { userRepository.changePasswordUser(recoveryModel) } returns Result.Error(exception)

        // When
        val result = changePasswordUserUseCase(recoveryModel)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userRepository.changePasswordUser(recoveryModel) }
    }

    @Test
    fun deleteUserUseCaseReturnsSuccessWhenUserIsDeleted() = runTest {
        // Given
        val userId = 1
        coEvery { userRepository.deleteUser(userId) } returns Result.Success(Unit)

        // When
        val result = deleteUserUseCase(userId)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userRepository.deleteUser(userId) }
    }

    @Test
    fun deleteUserUseCaseReturnsErrorWhenDeletionFails() = runTest {
        // Given
        val userId = 1
        val exception = Exception("Error al eliminar usuario")
        coEvery { userRepository.deleteUser(userId) } returns Result.Error(exception)

        // When
        val result = deleteUserUseCase(userId)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userRepository.deleteUser(userId) }
    }
}