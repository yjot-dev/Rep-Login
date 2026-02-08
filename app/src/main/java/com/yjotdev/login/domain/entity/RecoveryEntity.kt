package com.yjotdev.login.domain.entity

import com.google.gson.annotations.SerializedName

data class RecoveryEntity(
    @SerializedName("correo") var email: String = "",
    @SerializedName("clave") var password: String = ""
)