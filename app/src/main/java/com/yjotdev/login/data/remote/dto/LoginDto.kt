package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("nombre") var name: String = "",
    @SerializedName("clave") var password: String = ""
)