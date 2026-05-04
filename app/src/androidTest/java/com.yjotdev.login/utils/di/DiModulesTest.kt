package com.yjotdev.login.utils.di

import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Module
import dagger.Binds
import javax.inject.Singleton
import com.yjotdev.login.data.di.DiModules
import com.yjotdev.login.domain.repository.ConfigRepository
import com.yjotdev.login.domain.repository.UserRepository
import com.yjotdev.login.domain.repository.EmailRepository
import com.yjotdev.login.domain.repository.NotificationRepository
import com.yjotdev.login.domain.repository.PaymentRepository
import com.yjotdev.login.domain.repository.StringRepository
import com.yjotdev.login.utils.repository.FakeConfigRepositoryImpl
import com.yjotdev.login.utils.repository.FakeUserRepositoryImpl
import com.yjotdev.login.utils.repository.FakeEmailRepositoryImpl
import com.yjotdev.login.utils.repository.FakeNotificationRepositoryImpl
import com.yjotdev.login.utils.repository.FakePaymentRepositoryImpl
import com.yjotdev.login.utils.repository.FakeStringRepositoryImpl

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DiModules::class] // Nombre del módulo real
)
@Suppress("unused")
abstract class DiModulesTest {

    // --- BINDINGS (Abstracciones) ---
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: FakeUserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        impl: FakeEmailRepositoryImpl
    ): EmailRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: FakeNotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(
        impl: FakePaymentRepositoryImpl
    ): PaymentRepository

    @Binds
    @Singleton
    abstract fun bindStringRepository(
        impl: FakeStringRepositoryImpl
    ): StringRepository

    @Binds
    @Singleton
    abstract fun bindConfigRepository(
        impl: FakeConfigRepositoryImpl
    ): ConfigRepository

    // --- PROVIDERS (Instancias externas) ---
    // No son necesarios aqui
}