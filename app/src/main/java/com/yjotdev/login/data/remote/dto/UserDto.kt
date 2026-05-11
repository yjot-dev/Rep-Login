package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val name: String = "",
    @SerializedName("correo") val email: String = "",
    @SerializedName("clave") val password: String = "",
    @SerializedName("esInvitado") val isInvited: Boolean = false
)