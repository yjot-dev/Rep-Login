package com.yjotdev.login.domain.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("nombre") var name: String? = "",
    @SerializedName("correo") var email: String?= "",
    @SerializedName("clave") var password: String? = ""
)