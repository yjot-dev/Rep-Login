package com.yjotdev.login.presentation.mvvm.state

import com.yjotdev.login.domain.model.UserModel

data class UiState(
    val randomCode: Int = 0,
    val user: UserModel? = null,
    val isLoading: Boolean = false
)