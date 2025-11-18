package com.yjotdev.login.infrastructure.di

import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.yjotdev.login.domain.port.UserPort
import com.yjotdev.login.domain.port.EmailPort
import com.yjotdev.login.infrastructure.repository.UserRepository
import com.yjotdev.login.infrastructure.repository.EmailRepository

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class DiModules {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepository
    ): UserPort

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        impl: EmailRepository
    ): EmailPort
}