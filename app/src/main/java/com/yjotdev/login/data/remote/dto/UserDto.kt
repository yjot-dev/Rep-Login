package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("nombre") var name: String = "",
    @SerializedName("correo") var email: String = "",
    @SerializedName("clave") var password: String = ""
)