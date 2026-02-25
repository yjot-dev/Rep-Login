package com.yjotdev.login.application.mvvm.model

import com.yjotdev.login.domain.entity.UserEntity

data class UiModel(
    val randomCode: Int = 0,
    val user: UserEntity? = null,
    val isLoading: Boolean = false
)