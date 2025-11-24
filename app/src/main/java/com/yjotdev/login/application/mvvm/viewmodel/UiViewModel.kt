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
import kotlin.random.Random
import com.yjotdev.login.application.mvvm.model.UiModel
import com.yjotdev.login.domain.core.Result
import com.yjotdev.login.domain.entity.EmailEntity
import com.yjotdev.login.domain.entity.UserEntity
import com.yjotdev.login.domain.usecase.email.EmailUseCase
import com.yjotdev.login.domain.usecase.user.ChangePasswordUserUseCase
import com.yjotdev.login.domain.usecase.user.DeleteUserUseCase
import com.yjotdev.login.domain.usecase.user.FindUserUseCase
import com.yjotdev.login.domain.usecase.user.InsertUserUseCase
import com.yjotdev.login.domain.usecase.user.UpdateUserUseCase

@HiltViewModel
class UiViewModel @Inject constructor(
    private val findUserUseCase: FindUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val changePasswordUserUseCase: ChangePasswordUserUseCase,
    private val emailUseCase: EmailUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiModel())
    val uiState: StateFlow<UiModel> = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        cleanState()
    }
    /**
     * Limpia el estado del ViewModel
     */
    fun cleanState() {
        _uiState.value = UiModel()
    }
    /**
     * Cambia estado del codigo aleatorio para el usuario
     **/
    fun setRandomCode(randomCode: Int){
        _uiState.update { it.copy(randomCode = randomCode) }
    }
    /**
     * Cambia estado de la contraseña no encriptada del usuario
     **/
    fun setPassword(value: String){
        _uiState.update { it.copy(password = value) }
    }
    /**
     * Resetea los estados de error, wasFound, wasInserted, wasUpdated,
     * wasDeleted y wasEmailed para que los mensajes y acciones no se
     * repitan por cada cambio en los estados de la IU.
     */
    fun clearFlags() {
        _uiState.update { it.copy(error = null, wasFound = false,
            wasInserted = false, wasUpdated = false,
            wasDeleted = false, wasEmailed = false
        ) }
    }
    /**
     * Busca al usuario en la base de datos
     **/
    fun findUser(name: String, email: String, password: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = findUserUseCase(name, email,  password)
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = result.data,
                            wasFound = true,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = null,
                            wasFound = false,
                            error = result.exception.message ?: "Ocurrió un error desconocido"
                        )
                    }
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
            val result = insertUserUseCase(user)
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasInserted = true,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasInserted = false,
                            error = result.exception.message ?: "Ocurrió un error desconocido"
                        )
                    }
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
            val result = updateUserUseCase(id, user)
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasUpdated = true,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasUpdated = false,
                            error = result.exception.message ?: "Ocurrió un error desconocido"
                        )
                    }
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
            val result = deleteUserUseCase(id)
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasDeleted = true,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasDeleted = false,
                            error = result.exception.message ?: "Ocurrió un error desconocido"
                        )
                    }
                }
            }
        }
    }
    /**
     * Envia un email al usuario
     */
    fun sendEmail(to: String){
        val randomCode = Random.nextInt(9999 - 1000) + 1000
        setRandomCode(randomCode)
        val email = EmailEntity(
            to = to,
            subject = "Cambiar clave",
            text = "Su codigo de verificacion es: $randomCode"
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = emailUseCase(email)
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasEmailed = true,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasEmailed = false,
                            error = result.exception.message ?: "Ocurrió un error desconocido"
                        )
                    }
                }
            }
        }
    }
    /**
     * Actualiza la clave del usuario en la base de datos
     **/
    fun recoveryPassword(code: String, email: String, password: String){
        val currentState = _uiState.value
        if(code == currentState.randomCode.toString()){
            changePasswordUser(email, password)
        }else {
            _uiState.update { it.copy(error = "Codigo incorrecto") }
        }
    }

    private fun changePasswordUser(email: String, password: String){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = changePasswordUserUseCase(email, password)
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasUpdated = true,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wasUpdated = false,
                            error = result.exception.message ?: "Ocurrió un error desconocido"
                        )
                    }
                }
            }
        }
    }
}