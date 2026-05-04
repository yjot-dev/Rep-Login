package com.yjotdev.login.presentation.mvvm.state

import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.domain.model.UserModel

data class UiState(
    val randomCode: Int = 0,
    val user: UserModel? = null,
    val payments: List<PaymentModel> = emptyList(),
    val notifications: List<NotificationModel> = emptyList(),
    val isLoading: Boolean = false
)