package com.yjotdev.login.utils.di

import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Module
import dagger.Binds
import javax.inject.Singleton
import com.yjotdev.login.data.di.DiModules
import com.yjotdev.login.domain.repository.ConfigRepository
import com.yjotdev.login.domain.repository.UserApiRepository
import com.yjotdev.login.domain.repository.UserDaoRepository
import com.yjotdev.login.domain.repository.EmailApiRepository
import com.yjotdev.login.domain.repository.NotificationApiRepository
import com.yjotdev.login.domain.repository.NotificationDaoRepository
import com.yjotdev.login.domain.repository.PaymentApiRepository
import com.yjotdev.login.domain.repository.PaymentDaoRepository
import com.yjotdev.login.domain.repository.StringRepository
import com.yjotdev.login.utils.repository.FakeConfigRepositoryImpl
import com.yjotdev.login.utils.repository.FakeUserApiRepositoryImpl
import com.yjotdev.login.utils.repository.FakeUserDaoRepositoryImpl
import com.yjotdev.login.utils.repository.FakeEmailApiRepositoryImpl
import com.yjotdev.login.utils.repository.FakeNotificationApiRepositoryImpl
import com.yjotdev.login.utils.repository.FakeNotificationDaoRepositoryImpl
import com.yjotdev.login.utils.repository.FakePaymentApiRepositoryImpl
import com.yjotdev.login.utils.repository.FakePaymentDaoRepositoryImpl
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
    abstract fun bindUserApiRepository(
        impl: FakeUserApiRepositoryImpl
    ): UserApiRepository

    @Binds
    @Singleton
    abstract fun bindUserDaoRepository(
        impl: FakeUserDaoRepositoryImpl
    ): UserDaoRepository

    @Binds
    @Singleton
    abstract fun bindEmailApiRepository(
        impl: FakeEmailApiRepositoryImpl
    ): EmailApiRepository

    @Binds
    @Singleton
    abstract fun bindNotificationApiRepository(
        impl: FakeNotificationApiRepositoryImpl
    ): NotificationApiRepository

    @Binds
    @Singleton
    abstract fun bindNotificationDaoRepository(
        impl: FakeNotificationDaoRepositoryImpl
    ): NotificationDaoRepository

    @Binds
    @Singleton
    abstract fun bindPaymentApiRepository(
        impl: FakePaymentApiRepositoryImpl
    ): PaymentApiRepository

    @Binds
    @Singleton
    abstract fun bindPaymentDaoRepository(
        impl: FakePaymentDaoRepositoryImpl
    ): PaymentDaoRepository

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