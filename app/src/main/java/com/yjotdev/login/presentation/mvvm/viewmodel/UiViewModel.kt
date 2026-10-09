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
import com.yjotdev.login.domain.usecase.notification.GetRemoteNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.SendNotificationUseCase
import com.yjotdev.login.domain.usecase.notification.GetLocalNotificationsUseCase
import com.yjotdev.login.domain.usecase.notification.InsertLocalNotificationsUseCase
import com.yjotdev.login.domain.usecase.payment.GetRemotePaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.ValidatePaymentUseCase
import com.yjotdev.login.domain.usecase.payment.GetLocalPaymentsUseCase
import com.yjotdev.login.domain.usecase.payment.InsertLocalPaymentsUseCase
import com.yjotdev.login.domain.usecase.email.SendEmailUseCase
import com.yjotdev.login.domain.usecase.string.GetStringUseCase
import com.yjotdev.login.domain.usecase.user.SetPasswordRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.GetRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateRemoteUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertLocalUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateLocalUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteLocalUserUseCase
import com.yjotdev.login.domain.usecase.user.GetLocalUserUseCase
import com.yjotdev.login.domain.usecase.config.GetConfigUseCase
import com.yjotdev.login.R

@HiltViewModel
class UiViewModel @Inject constructor(
    private val getStringUseCase: GetStringUseCase,
    private val getRemoteUserUseCase: GetRemoteUserUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val insertRemoteUserUseCase: InsertRemoteUserUseCase,
    private val insertLocalUserUseCase: InsertLocalUserUseCase,
    private val updateRemoteUserUseCase: UpdateRemoteUserUseCase,
    private val updateLocalUserUseCase: UpdateLocalUserUseCase,
    private val deleteRemoteUserUseCase: DeleteRemoteUserUseCase,
    private val deleteLocalUserUseCase: DeleteLocalUserUseCase,
    private val setPasswordRemoteUserUseCase: SetPasswordRemoteUserUseCase,
    private val sendEmailUseCase: SendEmailUseCase,
    private val getRemotePaymentsUseCase: GetRemotePaymentsUseCase,
    private val getLocalPaymentsUseCase: GetLocalPaymentsUseCase,
    private val insertLocalPaymentsUseCase: InsertLocalPaymentsUseCase,
    private val validatePaymentUseCase: ValidatePaymentUseCase,
    private val getRemoteNotificationsUseCase: GetRemoteNotificationsUseCase,
    private val getLocalNotificationsUseCase: GetLocalNotificationsUseCase,
    private val insertLocalNotificationsUseCase: InsertLocalNotificationsUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val getConfigUseCase: GetConfigUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    private val _eventChannel = Channel<UiEvent>()
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val eventChannel = _eventChannel.receiveAsFlow()

    init { loadLocalUser() }

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
     * Cierra la sesión del usuario, borra al usuario en la base de datos local
     **/
    fun logoutUser() {
        viewModelScope.launch {
            deleteLocalUserUseCase(uiState.value.user)
            cleanState()
            _eventChannel.send(UiEvent.Navigate(
                R.id.action_user_to_login
            ))
        }
    }
    /**
     * Inicia la sesión del usuario, Crea al usuario en la base de datos local
     **/
    fun loginUser(nameOrEmail: String, password: String) {
        val login = LoginModel(nameOrEmail, password)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getRemoteUserUseCase(login)) {
                is Result.Success -> {
                    val user = result.data.copy(password = password)
                    insertLocalUserUseCase(user)
                    _uiState.update { it.copy(
                        user = user,
                        isCompletedLogin = true,
                        isLoading = false
                    )}
                }
                is Result.Error -> {
                    _uiState.update { it.copy(
                        user = UserModel(),
                        isCompletedLogin = false,
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
            when (val result = insertRemoteUserUseCase(user)) {
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
        val id = uiState.value.user.id
        val user = UserModel(id, name, email, password)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = updateRemoteUserUseCase(id, user)) {
                is Result.Success -> {
                    updateLocalUserUseCase(user)
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
        val id = uiState.value.user.id
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = deleteRemoteUserUseCase(id)) {
                is Result.Success -> {
                    deleteLocalUserUseCase(uiState.value.user)
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
            when (val result = setPasswordRemoteUserUseCase(recovery)) {
                is Result.Success -> {
                    val user = uiState.value.user.copy(password = password)
                    updateLocalUserUseCase(user)
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
        userId: Int = uiState.value.user.id,
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
                    syncPaymentsWithRemote(userId)
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
     * Obtiene todos los pagos del usuario en la base de datos local
     * o remota si es que no existen en local
     **/
    fun getPaymentsOfUser(limit: Int = Int.MAX_VALUE) {
        viewModelScope.launch {
            val userId = uiState.value.user.id
            getLocalPaymentsUseCase(userId, limit).collect { payments ->
                _uiState.update { it.copy(payments = payments) }
                if (payments.isEmpty()) {
                    syncPaymentsWithRemote(userId)
                }
            }
        }
    }
    /**
     * Obtiene todas las notificaciones del usuario en la base de datos local
     * o remota si es que no existen en local
     **/
    fun getNotificationsOfUser(limit: Int = Int.MAX_VALUE) {
        viewModelScope.launch {
            val userId = uiState.value.user.id
            getLocalNotificationsUseCase(userId, limit).collect { notifications ->
                _uiState.update { it.copy(notifications = notifications) }
                if (notifications.isEmpty()) {
                    syncNotificationsWithRemote(userId)
                }
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

    private fun loadLocalUser() {
        viewModelScope.launch {
            getLocalUserUseCase().collect { user ->
                _uiState.update { it.copy(user = user) }
                if (uiState.value.isCompletedLogin) {
                    _eventChannel.send(UiEvent.Navigate(
                        R.id.action_login_to_dashboard
                    ))
                    _eventChannel.send(UiEvent.ShowToast(
                        getStringUseCase(R.string.toast_login_success)
                    ))
                }
            }
        }
    }

    private suspend fun syncPaymentsWithRemote(userId: Int) {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = getRemotePaymentsUseCase(userId)) {
            is Result.Success -> {
                insertLocalPaymentsUseCase(result.data)
                _uiState.update { it.copy(isLoading = false) }
            }
            is Result.Error -> {
                _uiState.update { it.copy(isLoading = false) }
                _eventChannel.send(UiEvent.ShowLog(
                    result.exception.message!!
                ))
            }
        }
    }

    private suspend fun syncNotificationsWithRemote(userId: Int) {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = getRemoteNotificationsUseCase(userId)) {
            is Result.Success -> {
                insertLocalNotificationsUseCase(result.data)
                _uiState.update { it.copy(isLoading = false) }
            }
            is Result.Error -> {
                _uiState.update { it.copy(isLoading = false) }
                _eventChannel.send(UiEvent.ShowLog(
                    result.exception.message!!
                ))
            }
        }
    }

    private suspend fun executeSendNotification(date: String) {
        val body = SendNotificationRequestModel(
            userId = uiState.value.user.id,
            token = getConfigUseCase()["token"] ?: "",
            title = getStringUseCase(R.string.send_notification_title),
            body = getStringUseCase(
                R.string.send_notification_body,
                uiState.value.user.name
            ),
            date = date
        )
        when (val result = sendNotificationUseCase(body)) {
            is Result.Success -> {
                syncNotificationsWithRemote(uiState.value.user.id)
            }
            is Result.Error -> {
                _eventChannel.send(UiEvent.ShowLog(
                    result.exception.message!!
                ))
            }
        }
    }
}