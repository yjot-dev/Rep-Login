package com.yjotdev.login.application.mvvm.model

import com.yjotdev.login.domain.entity.UserEntity

data class UserModel(
    val randomCode: Int = 0,
    val error: String? = null,
    val user: UserEntity? = null,
    val password: String = "",
    val isLoading: Boolean = false,
    val wasFound: Boolean = false,
    val wasInserted: Boolean = false,
    val wasUpdated: Boolean = false,
    val wasDeleted: Boolean = false,
    val wasEmailed: Boolean = false
)