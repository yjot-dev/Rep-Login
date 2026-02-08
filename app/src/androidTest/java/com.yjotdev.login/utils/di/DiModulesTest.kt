package com.yjotdev.login.utils.di

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dagger.Binds
import javax.inject.Singleton
import com.yjotdev.login.infrastructure.di.DiModules
import com.yjotdev.login.domain.port.UserPort
import com.yjotdev.login.domain.port.EmailPort
import com.yjotdev.login.utils.repository.FakeUserRepository
import com.yjotdev.login.utils.repository.FakeEmailRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DiModules::class] // Nombre del módulo real
)
@Suppress("unused")
abstract class DiModulesTest {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: FakeUserRepository
    ): UserPort

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        impl: FakeEmailRepository
    ): EmailPort
}