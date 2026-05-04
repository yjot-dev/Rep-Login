package com.yjotdev.login

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.presentation.navigation.UiEvent
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.usecase.config.GetConfigUseCase
import com.yjotdev.login.domain.usecase.email.SendEmailUseCase
import com.yjotdev.login.domain.usecase.notification.SelectNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.SendNotificationUseCase
import com.yjotdev.login.domain.usecase.payment.CaptureOrderUseCase
import com.yjotdev.login.domain.usecase.payment.CreateOrderUseCase
import com.yjotdev.login.domain.usecase.payment.SelectPaymentsUseCase
import com.yjotdev.login.domain.usecase.string.GetStringUseCase
import com.yjotdev.login.domain.usecase.user.*

@ExperimentalCoroutinesApi
class UiViewModelTest {

    @RelaxedMockK
    private lateinit var getStringUseCase: GetStringUseCase
    @RelaxedMockK
    private lateinit var findUserUseCase: FindUserUseCase
    @RelaxedMockK
    private lateinit var insertUserUseCase: InsertUserUseCase
    @RelaxedMockK
    private lateinit var updateUserUseCase: UpdateUserUseCase
    @RelaxedMockK
    private lateinit var deleteUserUseCase: DeleteUserUseCase
    @RelaxedMockK
    private lateinit var changePasswordUserUseCase: ChangePasswordUserUseCase
    @RelaxedMockK
    private lateinit var sendEmailUseCase: SendEmailUseCase
    @RelaxedMockK
    private lateinit var createOrderUseCase: CreateOrderUseCase
    @RelaxedMockK
    private lateinit var captureOrderUseCase: CaptureOrderUseCase
    @RelaxedMockK
    private lateinit var selectPaymentsUseCase: SelectPaymentsUseCase
    @RelaxedMockK
    private lateinit var selectNotificationsUseCase: SelectNotificationsUseCase
    @RelaxedMockK
    private lateinit var sendNotificationUseCase: SendNotificationUseCase
    @RelaxedMockK
    private lateinit var getConfigUseCase: GetConfigUseCase

    private lateinit var viewModel: UiViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = UiViewModel(
            getStringUseCase,
            findUserUseCase,
            insertUserUseCase,
            updateUserUseCase,
            deleteUserUseCase,
            changePasswordUserUseCase,
            sendEmailUseCase,
            createOrderUseCase,
            captureOrderUseCase,
            selectPaymentsUseCase,
            selectNotificationsUseCase,
            sendNotificationUseCase,
            getConfigUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun whenLoginUserIsSuccessfulThenUiStateIsUpdatedAndEventsAreSent() = runTest {
        // Given
        val fakeUser = UserModel(id = 1, name = "testUser", email = "test@test.com", password = "password")
        val loginModel = LoginModel(name = "testUser", password = "password")
        val successMessage = "Login successful"
        coEvery { findUserUseCase(loginModel) } returns Result.Success(fakeUser)
        every { getStringUseCase(R.string.toast_login_success) } returns successMessage

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.Navigate(R.id.action_login_to_dashboard), awaitItem())
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val successState = awaitItem()
                assertFalse(successState.isLoading)
                assertEquals(fakeUser.copy(password = "password"), successState.user)
            }
        }

        // When
        viewModel.loginUser(loginModel.name, loginModel.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { findUserUseCase(loginModel) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_login_success) }
    }

    @Test
    fun whenLoginUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val loginModel = LoginModel("testUser", "wrongPassword")
        val exception = Exception("Invalid credentials")
        val toastMessage = "Login failed"
        coEvery { findUserUseCase(loginModel) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_login_error) } returns toastMessage

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
                assertNull(errorState.user)
            }
        }

        // When
        viewModel.loginUser(loginModel.name, loginModel.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { findUserUseCase(loginModel) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_login_error) }
    }

    @Test
    fun whenInsertUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToInsert = UserModel(name = "newUser", email = "new@test.com", password = "newPass")
        val successMessage = "User created"
        coEvery { insertUserUseCase(userToInsert) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_insert_success) } returns successMessage

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)
                val successState = awaitItem()
                assertFalse(successState.isLoading)
            }
        }

        // When
        viewModel.insertUser(userToInsert.name, userToInsert.email, userToInsert.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { insertUserUseCase(userToInsert) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_insert_success) }
    }

    @Test
    fun whenInsertUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val userToInsert = UserModel(name = "existingUser", email = "existing@test.com", password = "pass")
        val exception = Exception("User already exists")
        val toastMessage = "Insert failed"
        coEvery { insertUserUseCase(userToInsert) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_insert_error) } returns toastMessage

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)
                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
            }
        }

        // When
        viewModel.insertUser(userToInsert.name, userToInsert.email, userToInsert.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { insertUserUseCase(userToInsert) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_insert_error) }
    }

    @Test
    fun whenUpdateUserIsSuccessfulThenToastEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(id = 1, name = "oldName", email = "old@test.com", password = "oldPassword")
        val updatedUser = UserModel(id = 1, name = "newName", email = "new@test.com", password = "newPassword")
        val successMessage = "User updated"
        viewModel.setUser(initialUser)
        coEvery { updateUserUseCase(initialUser.id, updatedUser) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_update_success) } returns successMessage

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)
                val successState = awaitItem()
                assertFalse(successState.isLoading)
            }
        }

        // When
        viewModel.updateUser(updatedUser.name, updatedUser.email, updatedUser.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { updateUserUseCase(initialUser.id, updatedUser) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_update_success) }
    }

    @Test
    fun whenUpdateUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val initialUser = UserModel(id = 1, name = "oldName", email = "old@test.com", password = "oldPassword")
        val updatedUser = UserModel(id = 1, name = "newName", email = "new@test.com", password = "newPassword")
        val exception = Exception("Update failed")
        val toastMessage = "Update error"
        viewModel.setUser(initialUser)
        coEvery { updateUserUseCase(initialUser.id, updatedUser) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_update_error) } returns toastMessage

        // Then
        val job1 = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }
        val job2 = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)
                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
            }
        }

        // When
        viewModel.updateUser(updatedUser.name, updatedUser.email, updatedUser.password)
        advanceUntilIdle()

        job1.cancel()
        job2.cancel()

        coVerify(exactly = 1) { updateUserUseCase(initialUser.id, updatedUser) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_update_error) }
    }

    @Test
    fun whenDeleteUserIsSuccessfulThenToastEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(id = 1, name = "user", email = "user@test.com", password = "password")
        val successMessage = "User deleted"
        viewModel.setUser(initialUser)
        coEvery { deleteUserUseCase(initialUser.id) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_delete_success) } returns successMessage

        // Then
        val job = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }

        // When
        viewModel.deleteUser()
        advanceUntilIdle()

        job.cancel()

        coVerify(exactly = 1) { deleteUserUseCase(initialUser.id) }
    }

    @Test
    fun whenDeleteUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val initialUser = UserModel(id = 1, name = "user", email = "user@test.com", password = "password")
        val exception = Exception("Deletion failed")
        val toastMessage = "Delete error"
        viewModel.setUser(initialUser)
        coEvery { deleteUserUseCase(initialUser.id) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_delete_error) } returns toastMessage

        // Then
        val job = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }

        // When
        viewModel.deleteUser()
        advanceUntilIdle()

        job.cancel()

        coVerify(exactly = 1) { deleteUserUseCase(initialUser.id) }
    }

    @Test
    fun whenRecoveryPasswordIsCalledWithCorrectCodeThenChangePasswordIsCalled() = runTest {
        // Given
        val recoveryModel = RecoveryModel(email = "test@test.com", password = "newPassword")
        val successMessage = "Password updated"
        coEvery { changePasswordUserUseCase(recoveryModel) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_update_success) } returns successMessage

        // Then
        val job = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }

        // When
        viewModel.changePasswordUser(recoveryModel.email, recoveryModel.password)
        advanceUntilIdle()

        job.cancel()

        coVerify(exactly = 1) { changePasswordUserUseCase(recoveryModel) }
    }

    @Test
    fun whenRecoveryPasswordIsCalledWithIncorrectCodeThenToastEventIsSent() = runTest {
        // Given
        val recoveryModel = RecoveryModel(email = "test@test.com", password = "newPassword")
        val exception = Exception("Update failed")
        val errorMessage = "Password didn't update"
        coEvery { changePasswordUserUseCase(recoveryModel) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_update_error) } returns errorMessage

        // Then
        val job = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(errorMessage), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }

        // When
        viewModel.changePasswordUser(recoveryModel.email, recoveryModel.password)
        advanceUntilIdle()

        job.cancel()

        coVerify(exactly = 1) { changePasswordUserUseCase(recoveryModel) }
    }

    @Test
    fun whenSendEmailIsSuccessfulThenToastEventIsSent() = runTest {
        // Given
        val email = "test@test.com"
        val successMessage = "Email sent"
        coEvery { sendEmailUseCase(any()) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_emailsend_success) } returns successMessage

        // Then
        val job = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }

        // When
        viewModel.sendEmail(email, "Envio de email")
        advanceUntilIdle()

        job.cancel()

        coVerify(exactly = 1) { sendEmailUseCase(any()) }
    }

    @Test
    fun whenSendEmailFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val email = "test@test.com"
        val exception = Exception("Email service down")
        val toastMessage = "Email send error"
        coEvery { sendEmailUseCase(any()) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_emailsend_error) } returns toastMessage

        // Then
        val job = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog(exception.message!!), awaitItem())
            }
        }

        // When
        viewModel.sendEmail(email, "Envio de email")
        advanceUntilIdle()

        job.cancel()

        coVerify(exactly = 1) { sendEmailUseCase(any()) }
    }
}
