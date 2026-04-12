package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecoveryDto(
    @SerializedName("correo") var email: String = "",
    @SerializedName("clave") var password: String = ""
)