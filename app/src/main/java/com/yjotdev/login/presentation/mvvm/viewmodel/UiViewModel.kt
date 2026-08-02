package com.yjotdev.login.presentation.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.random.Random
import com.yjotdev.login.presentation.mvvm.state.UiState
import com.yjotdev.login.presentation.navigation.UiEvent
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.model.EmailModel
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.model.LoginModel
import com.yjotdev.login.domain.model.RecoveryModel
import com.yjotdev.login.domain.model.SendNotificationRequestModel
import com.yjotdev.login.domain.model.ValidateModel
import com.yjotdev.login.domain.usecase.notification.SelectNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.SendNotificationUseCase
import com.yjotdev.login.domain.usecase.payment.SelectPaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.ValidatePaymentUseCase
import com.yjotdev.login.domain.usecase.email.SendEmailUseCase
import com.yjotdev.login.domain.usecase.string.GetStringUseCase
import com.yjotdev.login.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.login.domain.usecase.user.FindUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateUserUseCase
import com.yjotdev.login.domain.usecase.config.GetConfigUseCase
import com.yjotdev.login.R

@HiltViewModel
class UiViewModel @Inject constructor(
    private val getStringUseCase: GetStringUseCase,
    private val findUserUseCase: FindUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val changePasswordUserUseCase: ChangePasswordUserUseCase,
    private val sendEmailUseCase: SendEmailUseCase,
    private val selectPaymentsUseCase: SelectPaymentsUseCase,
    private val validatePaymentUseCase: ValidatePaymentUseCase,
    private val selectNotificationsUseCase: SelectNotificationsUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val getConfigUseCase: GetConfigUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    private val _eventChannel = Channel<UiEvent>()
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val eventChannel = _eventChannel.receiveAsFlow()

    override fun onCleared() {
        cleanState()
    }
    /**
     * Limpia el estado del ViewModel
     */
    fun cleanState() {
        _uiState.value = UiState()
    }
    /**
     * Cambia estado del codigo aleatorio para el usuario
     **/
    fun setRandomCode(randomCode: Int) {
        _uiState.update { it.copy(randomCode = randomCode) }
    }
    /**
     * Cambia estado del usuario
     **/
    fun setUser(user: UserModel) {
        _uiState.update { it.copy(user = user) }
    }
    /**
     * Cierra la sesion del usuario
     **/
    fun logoutUser() {
        viewModelScope.launch {
            cleanState()
            _eventChannel.send(UiEvent.Navigate(
                R.id.action_user_to_login
            ))
        }
    }
    /**
     * Busca al usuario en la base de datos
     **/
    fun loginUser(nameOrEmail: String, password: String) {
        val login = LoginModel(nameOrEmail, password)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = findUserUseCase(login)) {
                is Result.Success -> {
                    _uiState.update { it.copy(
                        user = result.data.copy(password = password),
                        isLoading = false
                    )}
                    _eventChannel.send(UiEvent.Navigate(
                        R.id.action_login_to_dashboard
                    ))
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_login_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(
                        user = null,
                        isLoading = false
                    )}
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_login_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
    /**
     * Inserta al usuario en la base de datos
     */
    fun insertUser(name: String, email: String, password: String) {
        val user = UserModel(0, name, email, password)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = insertUserUseCase(user)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_insert_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_insert_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
    /**
     * Actualiza al usuario en la base de datos
     */
    fun updateUser(name: String, email: String, password: String) {
        val id = uiState.value.user?.id ?: 0
        val user = UserModel(id, name, email, password)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = updateUserUseCase(id, user)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_update_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_update_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
    /**
     * Elimina al usuario en la base de datos
     */
    fun deleteUser() {
        val id = uiState.value.user?.id ?: 0
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = deleteUserUseCase(id)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_delete_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_delete_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
    /**
     * Envia un email al usuario
     */
    fun sendEmail(to: String, subject: String) {
        val randomCode = Random.nextInt(9999 - 1000) + 1000
        setRandomCode(randomCode)
        val email = EmailModel(
            to = to,
            subject = subject,
            text = getStringUseCase(R.string.email_message, randomCode)
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = sendEmailUseCase(email)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_emailsend_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_emailsend_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
    /**
     * Actualiza la clave del usuario en la base de datos
     **/
    fun changePasswordUser(email: String, password: String) {
        val recovery = RecoveryModel(email, password)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = changePasswordUserUseCase(recovery)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_update_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_update_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }

    /**
     * Válida el pago del usuario en el backend con la Google Play Developer API
     **/
    fun validatePayment(
        purchaseToken: String,
        productId: String?,
        userId: Int = uiState.value.user?.id ?: 0,
        amount: Float,
        money: String,
        date: String
    ) {
        val validate = ValidateModel(purchaseToken, productId, userId, amount, money, date)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = validatePaymentUseCase(validate)) {
                is Result.Success -> {
                    executeSendNotification(date)
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_payment_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_payment_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
    /**
     * selecciona todos los pagos del usuario en la base de datos
     **/
    fun selectPayments(maxRows: Int? = null) {
        val userId = uiState.value.user?.id ?: 0
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = selectPaymentsUseCase(userId, maxRows)) {
                is Result.Success -> {
                    _uiState.update { it.copy(
                        payments = result.data,
                        isLoading = false
                    )}
                }
                is Result.Error -> {
                    _uiState.update { it.copy(
                        payments = emptyList(),
                        isLoading = false
                    )}
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
    /**
     * Selecciona todas las notificaciones del usuario en la base de datos
     **/
    fun selectNotifications(maxRows: Int? = null) {
        val userId = uiState.value.user?.id ?: 0
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = selectNotificationsUseCase(userId, maxRows)) {
                is Result.Success -> {
                    _uiState.update { it.copy(
                        notifications = result.data,
                        isLoading = false
                    )}
                }
                is Result.Error -> {
                    _uiState.update { it.copy(
                        notifications = emptyList(),
                        isLoading = false
                    )}
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }

    private suspend fun executeSendNotification(date: String) {
        val body = SendNotificationRequestModel(
            userId = uiState.value.user?.id ?: 0,
            token = getConfigUseCase()["token"] ?: "",
            title = getStringUseCase(R.string.send_notification_title),
            body = getStringUseCase(
                R.string.send_notification_body,
                uiState.value.user?.name ?: ""
            ),
            date = date
        )
        when (val result = sendNotificationUseCase(body)) {
            is Result.Success -> {}
            is Result.Error -> {
                _eventChannel.send(UiEvent.ShowLog(
                    result.exception.message!!
                ))
            }
        }
    }
    /**
     * Envia una notificacion del usuario mediante la API
     **/
    fun sendNotification(date: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            executeSendNotification(date)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}