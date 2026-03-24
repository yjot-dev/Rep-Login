package com.yjotdev.login.application.mvvm.viewmodel

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
import com.yjotdev.login.application.mvvm.model.UiModel
import com.yjotdev.login.application.navigation.UiEvent
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.EmailEntity
import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.entity.LoginEntity
import com.yjotdev.login.domain.entity.RecoveryEntity
import com.yjotdev.login.domain.usecase.email.EmailUseCase
import com.yjotdev.login.domain.usecase.string.StringUseCase
import com.yjotdev.login.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.login.domain.usecase.user.FindUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateUserUseCase
import com.yjotdev.login.R

@HiltViewModel
class UiViewModel @Inject constructor(
    private val getString: StringUseCase,
    private val findUserUseCase: FindUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val changePasswordUserUseCase: ChangePasswordUserUseCase,
    private val emailUseCase: EmailUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiModel())
    private val _eventChannel = Channel<UiEvent>()
    val uiState: StateFlow<UiModel> = _uiState.asStateFlow()
    val eventChannel = _eventChannel.receiveAsFlow()

    override fun onCleared() {
        super.onCleared()
        cleanState()
    }
    /**
     * Limpia el estado del ViewModel
     */
    fun cleanState(){
        _uiState.value = UiModel()
    }
    /**
     * Cambia estado del codigo aleatorio para el usuario
     **/
    fun setRandomCode(randomCode: Int){
        _uiState.update { it.copy(randomCode = randomCode) }
    }
    /**
     * Cambia estado del usuario
     **/
    fun setUser(user: UserEntity){
        _uiState.update { it.copy(user = user) }
    }
    /**
     * Cierra la sesion del usuario
     **/
    fun logoutUser(){
        viewModelScope.launch {
            _eventChannel.send(UiEvent.Navigate(
                R.id.action_user_to_login
            ))
        }
    }
    /**
     * Busca al usuario en la base de datos
     **/
    fun loginUser(nameOrEmail: String, password: String){
        val login = LoginEntity(
            name = nameOrEmail,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = findUserUseCase(login)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = result.data.copy(password = password)
                        )
                    }
                    _eventChannel.send(UiEvent.Navigate(
                        R.id.action_login_to_user
                    ))
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_login_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = null
                        )
                    }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_login_error)
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
    fun insertUser(name: String, email: String, password: String){
        val user = UserEntity(
            name = name,
            email = email,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = insertUserUseCase(user)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_insert_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_insert_error)
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
    fun updateUser(name: String, email: String, password: String){
        val id = uiState.value.user?.id ?: 0
        val user = UserEntity(
            name = name,
            email = email,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = updateUserUseCase(id, user)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_update_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_update_error)
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
    fun deleteUser(){
        val id = uiState.value.user?.id ?: 0
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = deleteUserUseCase(id)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_delete_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_delete_error)
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
    fun sendEmail(to: String, subject: String){
        val randomCode = Random.nextInt(9999 - 1000) + 1000
        setRandomCode(randomCode)
        val email = EmailEntity(
            to = to,
            subject = subject,
            text = getString(R.string.email_message, randomCode)
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = emailUseCase(email)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_emailsend_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_emailsend_error)
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
    fun changePasswordUser(email: String, password: String){
        val recovery = RecoveryEntity(
            email = email,
            password = password
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = changePasswordUserUseCase(recovery)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_update_success)
                    ))
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _eventChannel.send(UiEvent.ShowToast(
                        getString(R.string.toast_update_error)
                    ))
                    _eventChannel.send(UiEvent.ShowLog(
                        result.exception.message!!
                    ))
                }
            }
        }
    }
}