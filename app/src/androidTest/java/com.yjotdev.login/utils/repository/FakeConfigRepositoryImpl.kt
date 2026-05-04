package com.yjotdev.login.utils.repository

import javax.inject.Singleton
import javax.inject.Inject
import com.yjotdev.login.domain.repository.ConfigRepository

@Singleton
class FakeConfigRepositoryImpl @Inject constructor() : ConfigRepository {
    private val config = mutableMapOf<String, String?>()

    override fun saveConfig(user: MutableMap<String, String>) {
        config["token"] = user["token"]
    }

    override fun getConfig(): MutableMap<String, String?> {
        return config
    }
}