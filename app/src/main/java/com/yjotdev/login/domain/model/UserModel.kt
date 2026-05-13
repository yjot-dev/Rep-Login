package com.yjotdev.login.domain.model

data class UserModel(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isInvited: Boolean = false,
    val isInWhiteList: Boolean = false
)