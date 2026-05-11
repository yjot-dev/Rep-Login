package com.yjotdev.login.utils.repository

import javax.inject.Singleton
import javax.inject.Inject
import com.yjotdev.login.domain.repository.ConfigRepository

@Singleton
class FakeConfigRepositoryImpl @Inject constructor() : ConfigRepository {
    private val config = mutableMapOf<String, String?>()

    override fun saveTokenFCM(token: String) {
        config["token"] = token
    }

    override fun getConfig(): MutableMap<String, String?> {
        return config
    }
}