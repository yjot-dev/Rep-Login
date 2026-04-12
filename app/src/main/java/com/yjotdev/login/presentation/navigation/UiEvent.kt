package com.yjotdev.login.presentation.navigation

sealed class UiEvent {
    data class Navigate(val resId: Int) : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
    data class ShowLog(val message: String) : UiEvent()
}