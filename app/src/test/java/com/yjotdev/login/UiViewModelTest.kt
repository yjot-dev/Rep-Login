package com.yjotdev.login

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.model.SendNotificationRequestModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.model.ValidateModel
import com.yjotdev.login.domain.usecase.config.GetConfigUseCase
import com.yjotdev.login.domain.usecase.email.SendEmailUseCase
import com.yjotdev.login.domain.usecase.notification.GetRemoteNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.GetLocalNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.InsertLocalNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.SendNotificationUseCase
import com.yjotdev.login.domain.usecase.payment.GetRemotePaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.GetLocalPaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.InsertLocalPaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.ValidatePaymentUseCase
import com.yjotdev.login.domain.usecase.string.GetStringUseCase
import com.yjotdev.login.domain.usecase.user.SetPasswordRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteLocalUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.GetRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.GetLocalUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertLocalUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateLocalUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateRemoteUserUseCase
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.presentation.navigation.UiEvent

@OptIn(ExperimentalCoroutinesApi::class)
class UiViewModelTest {

    @RelaxedMockK
    private lateinit var getStringUseCase: GetStringUseCase

    @RelaxedMockK
    private lateinit var getRemoteUserUseCase: GetRemoteUserUseCase

    @RelaxedMockK
    private lateinit var getLocalUserUseCase: GetLocalUserUseCase

    @RelaxedMockK
    private lateinit var insertRemoteUserUseCase: InsertRemoteUserUseCase

    @RelaxedMockK
    private lateinit var insertLocalUserUseCase: InsertLocalUserUseCase

    @RelaxedMockK
    private lateinit var updateRemoteUserUseCase: UpdateRemoteUserUseCase

    @RelaxedMockK
    private lateinit var updateLocalUserUseCase: UpdateLocalUserUseCase

    @RelaxedMockK
    private lateinit var deleteRemoteUserUseCase: DeleteRemoteUserUseCase

    @RelaxedMockK
    private lateinit var deleteLocalUserUseCase: DeleteLocalUserUseCase

    @RelaxedMockK
    private lateinit var setPasswordRemoteUserUseCase: SetPasswordRemoteUserUseCase

    @RelaxedMockK
    private lateinit var sendEmailUseCase: SendEmailUseCase

    @RelaxedMockK
    private lateinit var getRemotePaymentsUseCase: GetRemotePaymentsUseCase

    @RelaxedMockK
    private lateinit var getLocalPaymentsUseCase: GetLocalPaymentsUseCase

    @RelaxedMockK
    private lateinit var insertLocalPaymentsUseCase: InsertLocalPaymentsUseCase

    @RelaxedMockK
    private lateinit var validatePaymentUseCase: ValidatePaymentUseCase

    @RelaxedMockK
    private lateinit var getRemoteNotificationsUseCase: GetRemoteNotificationsUseCase

    @RelaxedMockK
    private lateinit var getLocalNotificationsUseCase: GetLocalNotificationsUseCase

    @RelaxedMockK
    private lateinit var insertLocalNotificationsUseCase: InsertLocalNotificationsUseCase

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
            getRemoteUserUseCase,
            getLocalUserUseCase,
            insertRemoteUserUseCase,
            insertLocalUserUseCase,
            updateRemoteUserUseCase,
            updateLocalUserUseCase,
            deleteRemoteUserUseCase,
            deleteLocalUserUseCase,
            setPasswordRemoteUserUseCase,
            sendEmailUseCase,
            getRemotePaymentsUseCase,
            getLocalPaymentsUseCase,
            insertLocalPaymentsUseCase,
            validatePaymentUseCase,
            getRemoteNotificationsUseCase,
            getLocalNotificationsUseCase,
            insertLocalNotificationsUseCase,
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
    fun whenLoginUserIsSuccessfulFromRemoteThenUiStateIsUpdatedAndNavigateEventIsSent() = runTest {
        // Given
        val fakeUserRemote = UserModel(
            id = 1,
            name = "testUser",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        val fakeLoginModel = LoginModel(
            name = "testUser",
            password = "password"
        )
        val localUserFlow = MutableStateFlow(UserModel())
        coEvery { getRemoteUserUseCase(fakeLoginModel) } returns Result.Success(fakeUserRemote)
        coEvery { insertLocalUserUseCase(fakeUserRemote) } answers {
            localUserFlow.value = fakeUserRemote
        }

        // When
        viewModel.loginUser("testUser", "password")
        advanceUntilIdle()

        // Then
        assertEquals(fakeUserRemote, viewModel.uiState.value.user)
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemoteUserUseCase(fakeLoginModel) }
        coVerify(exactly = 1) { insertLocalUserUseCase(fakeUserRemote) }
    }

    @Test
    fun whenLoginUserFailsFromRemoteThenToastAndLogEventsAreSent() = runTest {
        // Given
        val fakeUserLocal = UserModel(
            id = 0,
            name = "",
            email = "",
            password = "",
            isInvited = false,
            isInWhiteList = false
        )
        val fakeLoginModel = LoginModel(
            name = "testUser",
            password = "wrongPassword"
        )
        val exception = Exception("Invalid credentials")
        val toastMessage = "Login failed"
        every { getLocalUserUseCase() } returns flowOf(fakeUserLocal)
        coEvery { getRemoteUserUseCase(fakeLoginModel) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_login_error) } returns toastMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog("Invalid credentials"), awaitItem())
            }
        }
        viewModel.loginUser("testUser", "wrongPassword")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertEquals(UserModel(), viewModel.uiState.value.user)
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemoteUserUseCase(fakeLoginModel) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_login_error) }
    }

    @Test
    fun whenInsertUserIsSuccessfulThenShowToastEventIsSent() = runTest {
        // Given
        val userToInsert = UserModel(
            id = 0,
            name = "newUser",
            email = "new@test.com",
            password = "newPassword",
            isInvited = false,
            isInWhiteList = false
        )
        val successMessage = "User inserted"
        coEvery { insertRemoteUserUseCase(userToInsert) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_insert_success) } returns successMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        viewModel.insertUser("newUser", "new@test.com", "newPassword")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { insertRemoteUserUseCase(userToInsert) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_insert_success) }
    }

    @Test
    fun whenInsertUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val userToInsert = UserModel(
            id = 0,
            name = "newUser",
            email = "new@test.com",
            password = "newPassword",
            isInvited = false,
            isInWhiteList = false
        )
        val exception = Exception("Database error")
        val toastMessage = "Insert error"
        coEvery { insertRemoteUserUseCase(userToInsert) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_insert_error) } returns toastMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog("Database error"), awaitItem())
            }
        }
        viewModel.insertUser("newUser", "new@test.com", "newPassword")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { insertRemoteUserUseCase(userToInsert) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_insert_error) }
    }

    @Test
    fun whenUpdateUserIsSuccessfulThenLocalUserIsUpdatedAndToastEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "oldName",
            email = "old@test.com",
            password = "oldPassword",
            isInvited = false,
            isInWhiteList = false
        )
        val updatedUser = UserModel(
            id = 1,
            name = "newName",
            email = "new@test.com",
            password = "newPassword",
            isInvited = false,
            isInWhiteList = false
        )
        val successMessage = "User updated"
        viewModel.setUser(initialUser)
        coEvery { updateRemoteUserUseCase(1, updatedUser) } returns Result.Success(Unit)
        coEvery { updateLocalUserUseCase(updatedUser) } returns Unit
        every { getStringUseCase(R.string.toast_update_success) } returns successMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        viewModel.updateUser("newName", "new@test.com", "newPassword")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { updateRemoteUserUseCase(1, updatedUser) }
        coVerify(exactly = 1) { updateLocalUserUseCase(updatedUser) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_update_success) }
    }

    @Test
    fun whenUpdateUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "oldName",
            email = "old@test.com",
            password = "oldPassword",
            isInvited = false,
            isInWhiteList = false
        )
        val updatedUser = UserModel(
            id = 1,
            name = "newName",
            email = "new@test.com",
            password = "newPassword",
            isInvited = false,
            isInWhiteList = false
        )
        val exception = Exception("Update failed")
        val toastMessage = "Update error"
        viewModel.setUser(initialUser)
        coEvery { updateRemoteUserUseCase(1, updatedUser) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_update_error) } returns toastMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog("Update failed"), awaitItem())
            }
        }
        viewModel.updateUser("newName", "new@test.com", "newPassword")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { updateRemoteUserUseCase(1, updatedUser) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_update_error) }
    }

    @Test
    fun whenDeleteUserIsSuccessfulThenToastEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "user@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        val successMessage = "User deleted"
        viewModel.setUser(initialUser)
        coEvery { deleteRemoteUserUseCase(1) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_delete_success) } returns successMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        viewModel.deleteUser()
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { deleteRemoteUserUseCase(1) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_delete_success) }
    }

    @Test
    fun whenDeleteUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "user@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        val exception = Exception("Deletion failed")
        val toastMessage = "Delete error"
        viewModel.setUser(initialUser)
        coEvery { deleteRemoteUserUseCase(1) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_delete_error) } returns toastMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog("Deletion failed"), awaitItem())
            }
        }
        viewModel.deleteUser()
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { deleteRemoteUserUseCase(1) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_delete_error) }
    }

    @Test
    fun whenSendEmailIsSuccessfulThenToastEventIsSent() = runTest {
        // Given
        val emailTo = "test@test.com"
        val emailSubject = "Subject"
        val successMessage = "Email sent"
        every { getStringUseCase(R.string.email_message, any()) } returns "Code: 1234"
        coEvery { sendEmailUseCase(any()) } returns Result.Success(Unit)
        every { getStringUseCase(R.string.toast_emailsend_success) } returns successMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        viewModel.sendEmail(emailTo, emailSubject)
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { sendEmailUseCase(any()) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_emailsend_success) }
    }

    @Test
    fun whenSendEmailFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val emailTo = "test@test.com"
        val emailSubject = "Subject"
        val exception = Exception("Email send error")
        val toastMessage = "Email error"
        every { getStringUseCase(R.string.email_message, any()) } returns "Code: 1234"
        coEvery { sendEmailUseCase(any()) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_emailsend_error) } returns toastMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog("Email send error"), awaitItem())
            }
        }
        viewModel.sendEmail(emailTo, emailSubject)
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { sendEmailUseCase(any()) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_emailsend_error) }
    }

    @Test
    fun whenChangePasswordUserIsSuccessfulThenLocalUserIsUpdatedAndToastEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "oldPassword",
            isInvited = false,
            isInWhiteList = false
        )
        val updatedUser = initialUser.copy(password = "newPassword")
        val recoveryModel = RecoveryModel(
            email = "test@test.com",
            password = "newPassword"
        )
        val successMessage = "Password updated"
        viewModel.setUser(initialUser)
        coEvery { setPasswordRemoteUserUseCase(recoveryModel) } returns Result.Success(Unit)
        coEvery { updateLocalUserUseCase(updatedUser) } returns Unit
        every { getStringUseCase(R.string.toast_update_success) } returns successMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        viewModel.changePasswordUser("test@test.com", "newPassword")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { setPasswordRemoteUserUseCase(recoveryModel) }
        coVerify(exactly = 1) { updateLocalUserUseCase(updatedUser) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_update_success) }
    }

    @Test
    fun whenChangePasswordUserFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val recoveryModel = RecoveryModel(
            email = "test@test.com",
            password = "newPassword"
        )
        val exception = Exception("Password change error")
        val toastMessage = "Update error"
        coEvery { setPasswordRemoteUserUseCase(recoveryModel) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_update_error) } returns toastMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog("Password change error"), awaitItem())
            }
        }
        viewModel.changePasswordUser("test@test.com", "newPassword")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { setPasswordRemoteUserUseCase(recoveryModel) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_update_error) }
    }

    @Test
    fun whenGetPaymentsOfUserLocalIsEmptyThenFetchFromRemoteAndInsertToLocal() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val fakePayments = listOf(
            PaymentModel(
                id = 10,
                amount = 100.0f,
                money = "USD",
                status = "Comprado",
                date = "2024-01-01",
                userId = 1
            )
        )
        val paymentsFlow = MutableStateFlow<List<PaymentModel>>(emptyList())
        every { getLocalPaymentsUseCase(1, Int.MAX_VALUE) } returns paymentsFlow
        coEvery { getRemotePaymentsUseCase(1) } returns Result.Success(fakePayments)
        coEvery { insertLocalPaymentsUseCase(fakePayments) } answers {
            paymentsFlow.value = fakePayments
        }

        // When
        viewModel.getPaymentsOfUser()
        advanceUntilIdle()

        // Then
        assertEquals(fakePayments, viewModel.uiState.value.payments)
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemotePaymentsUseCase(1) }
        coVerify(exactly = 1) { insertLocalPaymentsUseCase(fakePayments) }
    }

    @Test
    fun whenGetPaymentsOfUserLocalIsEmptyAndRemoteFailsThenLogEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val exception = Exception("Payments fetch error")
        every { getLocalPaymentsUseCase(1, Int.MAX_VALUE) } returns flowOf(emptyList())
        coEvery { getRemotePaymentsUseCase(1) } returns Result.Error(exception)

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowLog("Payments fetch error"), awaitItem())
            }
        }
        viewModel.getPaymentsOfUser()
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertTrue(viewModel.uiState.value.payments.isEmpty())
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemotePaymentsUseCase(1) }
    }

    @Test
    fun whenValidatePaymentIsSuccessfulThenNotificationIsSentAndToastEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val validateModel = ValidateModel(
            purchaseToken = "token123",
            productId = "prod1",
            userId = 1,
            amount = 10.0f,
            money = "USD",
            date = "2024-01-01"
        )
        val successMessage = "Payment validated"
        coEvery { validatePaymentUseCase(validateModel) } returns Result.Success(Unit)
        coEvery { getConfigUseCase() } returns mutableMapOf("token" to "token123")
        every { getStringUseCase(R.string.send_notification_title) } returns "Title"
        every { getStringUseCase(R.string.send_notification_body, "user") } returns "Body user"
        coEvery { sendNotificationUseCase(any()) } returns Result.Success(Unit)
        coEvery { getRemoteNotificationsUseCase(1) } returns Result.Success(emptyList())
        coEvery { getRemotePaymentsUseCase(1) } returns Result.Success(emptyList())
        every { getStringUseCase(R.string.toast_payment_success) } returns successMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(successMessage), awaitItem())
            }
        }
        viewModel.validatePayment(
            purchaseToken = "token123",
            productId = "prod1",
            userId = 1,
            amount = 10.0f,
            money = "USD",
            date = "2024-01-01"
        )
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { validatePaymentUseCase(validateModel) }
        coVerify(exactly = 1) { sendNotificationUseCase(any()) }
        coVerify(exactly = 1) { getRemoteNotificationsUseCase(1) }
        coVerify(exactly = 1) { getRemotePaymentsUseCase(1) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_payment_success) }
    }

    @Test
    fun whenValidatePaymentFailsThenToastAndLogEventsAreSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val validateModel = ValidateModel(
            purchaseToken = "token123",
            productId = "prod1",
            userId = 1,
            amount = 10.0f,
            money = "USD",
            date = "2024-01-01"
        )
        val exception = Exception("Validation error")
        val toastMessage = "Payment error"
        coEvery { validatePaymentUseCase(validateModel) } returns Result.Error(exception)
        every { getStringUseCase(R.string.toast_payment_error) } returns toastMessage

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowToast(toastMessage), awaitItem())
                assertEquals(UiEvent.ShowLog("Validation error"), awaitItem())
            }
        }
        viewModel.validatePayment(
            purchaseToken = "token123",
            productId = "prod1",
            userId = 1,
            amount = 10.0f,
            money = "USD",
            date = "2024-01-01"
        )
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { validatePaymentUseCase(validateModel) }
        coVerify(exactly = 1) { getStringUseCase(R.string.toast_payment_error) }
    }

    @Test
    fun whenGetNotificationsOfUserLocalIsEmptyThenFetchFromRemoteAndInsertToLocal() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val fakeNotifications = listOf(
            NotificationModel(
                id = 5,
                message = "Msg",
                date = "2024-01-01",
                userId = 1
            )
        )
        val notificationsFlow = MutableStateFlow<List<NotificationModel>>(emptyList())
        every { getLocalNotificationsUseCase(1, Int.MAX_VALUE) } returns notificationsFlow
        coEvery { getRemoteNotificationsUseCase(1) } returns Result.Success(fakeNotifications)
        coEvery { insertLocalNotificationsUseCase(fakeNotifications) } answers {
            notificationsFlow.value = fakeNotifications
        }

        // When
        viewModel.getNotificationsOfUser()
        advanceUntilIdle()

        // Then
        assertEquals(fakeNotifications, viewModel.uiState.value.notifications)
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemoteNotificationsUseCase(1) }
        coVerify(exactly = 1) { insertLocalNotificationsUseCase(fakeNotifications) }
    }

    @Test
    fun whenGetNotificationsOfUserLocalIsEmptyAndRemoteFailsThenLogEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val exception = Exception("Notifications error")
        every { getLocalNotificationsUseCase(1, Int.MAX_VALUE) } returns flowOf(emptyList())
        coEvery { getRemoteNotificationsUseCase(1) } returns Result.Error(exception)

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowLog("Notifications error"), awaitItem())
            }
        }
        viewModel.getNotificationsOfUser()
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertTrue(viewModel.uiState.value.notifications.isEmpty())
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getRemoteNotificationsUseCase(1) }
    }

    @Test
    fun whenSendNotificationIsSuccessfulThenNotificationUseCaseIsCalled() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val requestModel = SendNotificationRequestModel(
            userId = 1,
            token = "token123",
            title = "Title",
            body = "Body user",
            date = "2024-01-01"
        )
        coEvery { getConfigUseCase() } returns mutableMapOf("token" to "token123")
        every { getStringUseCase(R.string.send_notification_title) } returns "Title"
        every { getStringUseCase(R.string.send_notification_body, "user") } returns "Body user"
        coEvery { sendNotificationUseCase(requestModel) } returns Result.Success(Unit)
        coEvery { getRemoteNotificationsUseCase(1) } returns Result.Success(emptyList())

        // When
        viewModel.sendNotification("2024-01-01")
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { sendNotificationUseCase(requestModel) }
    }

    @Test
    fun whenSendNotificationFailsThenLogEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)
        val requestModel = SendNotificationRequestModel(
            userId = 1,
            token = "token123",
            title = "Title",
            body = "Body user",
            date = "2024-01-01"
        )
        val exception = Exception("Send notification error")
        coEvery { getConfigUseCase() } returns mutableMapOf("token" to "token123")
        every { getStringUseCase(R.string.send_notification_title) } returns "Title"
        every { getStringUseCase(R.string.send_notification_body, "user") } returns "Body user"
        coEvery { sendNotificationUseCase(requestModel) } returns Result.Error(exception)

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.ShowLog("Send notification error"), awaitItem())
            }
        }
        viewModel.sendNotification("2024-01-01")
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { sendNotificationUseCase(requestModel) }
    }

    @Test
    fun whenLogoutUserIsCalledThenStateIsCleanedAndNavigateEventIsSent() = runTest {
        // Given
        val initialUser = UserModel(
            id = 1,
            name = "user",
            email = "test@test.com",
            password = "password",
            isInvited = false,
            isInWhiteList = false
        )
        viewModel.setUser(initialUser)

        // When
        val eventJob = launch {
            viewModel.eventChannel.test {
                assertEquals(UiEvent.Navigate(R.id.action_user_to_login), awaitItem())
            }
        }
        viewModel.logoutUser()
        advanceUntilIdle()
        eventJob.cancel()

        // Then
        assertEquals(UserModel(), viewModel.uiState.value.user)
    }
}
