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
import com.yjotdev.login.domain.entity.LoginEntity
import com.yjotdev.login.domain.entity.RecoveryEntity
import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.port.UserPort
import com.yjotdev.login.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.login.domain.usecase.user.FindUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateUserUseCase

/**
 * Pruebas unitarias para los casos de uso CRUD de Usuarios.
 */
class UserUseCaseTest {

    private lateinit var userPort: UserPort

    private lateinit var findUserUseCase: FindUserUseCase
    private lateinit var insertUserUseCase: InsertUserUseCase
    private lateinit var updateUserUseCase: UpdateUserUseCase
    private lateinit var changePasswordUserUseCase: ChangePasswordUserUseCase
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun setUp() {
        userPort = mockk()
        findUserUseCase = FindUserUseCase(userPort)
        insertUserUseCase = InsertUserUseCase(userPort)
        updateUserUseCase = UpdateUserUseCase(userPort)
        changePasswordUserUseCase = ChangePasswordUserUseCase(userPort)
        deleteUserUseCase = DeleteUserUseCase(userPort)
    }

    @Test
    fun whenFindUserUseCaseIsInvokedSuccessfullyThenItReturnsAUser() = runTest {
        // Given
        val loginEntity = LoginEntity("testuser", "password")
        val fakeUser = UserEntity(id = 1, name = "Test User", email = "test@example.com", password = "password")
        coEvery { userPort.findUser(loginEntity) } returns Result.Success(fakeUser)

        // When
        val result = findUserUseCase(loginEntity)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(fakeUser, (result as Result.Success).data)
        coVerify(exactly = 1) { userPort.findUser(loginEntity) }
    }

    @Test
    fun whenFindUserUseCaseFailsThenItReturnsAnError() = runTest {
        // Given
        val loginEntity = LoginEntity("testuser", "password")
        val exception = Exception("User not found")
        coEvery { userPort.findUser(loginEntity) } returns Result.Error(exception)

        // When
        val result = findUserUseCase(loginEntity)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { userPort.findUser(loginEntity) }
    }

    @Test
    fun whenInsertUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val newUser = UserEntity(name = "New User", email = "new@example.com", password = "newpassword")
        coEvery { userPort.insertUser(newUser) } returns Result.Success(Unit)

        // When
        val result = insertUserUseCase(newUser)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userPort.insertUser(newUser) }
    }

    @Test
    fun whenUpdateUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val userId = 1
        val userToUpdate = UserEntity(name = "Updated User", email = "updated@example.com", password = "updatedpassword")
        coEvery { userPort.updateUser(userId, userToUpdate) } returns Result.Success(Unit)

        // When
        val result = updateUserUseCase(userId, userToUpdate)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userPort.updateUser(userId, userToUpdate) }
    }

    @Test
    fun whenChangePasswordUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val recoveryEntity = RecoveryEntity(email = "test@example.com", password = "newpassword")
        coEvery { userPort.changePasswordUser(recoveryEntity) } returns Result.Success(Unit)

        // When
        val result = changePasswordUserUseCase(recoveryEntity)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userPort.changePasswordUser(recoveryEntity) }
    }

    @Test
    fun whenDeleteUserUseCaseIsInvokedThenPortMethodIsCalled() = runTest {
        // Given
        val userId = 1
        coEvery { userPort.deleteUser(userId) } returns Result.Success(Unit)

        // When
        val result = deleteUserUseCase(userId)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { userPort.deleteUser(userId) }
    }
}
