package com.yjotdev.login.data.remote.network

object TokenProvider {
    @Volatile
    var firebaseToken: String? = null
}