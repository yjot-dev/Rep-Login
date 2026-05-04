package com.yjotdev.login.data.di

import dagger.hilt.components.SingletonComponent
import dagger.Module
import dagger.Binds
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import retrofit2.Retrofit
import com.yjotdev.login.data.repository.UserRepositoryImpl
import com.yjotdev.login.data.repository.EmailRepositoryImpl
import com.yjotdev.login.data.repository.StringRepositoryImpl
import com.yjotdev.login.data.remote.network.RetrofitBuilder
import com.yjotdev.login.data.remote.service.EmailService
import com.yjotdev.login.data.remote.service.NotificationService
import com.yjotdev.login.data.remote.service.PaymentService
import com.yjotdev.login.data.remote.service.UserService
import com.yjotdev.login.data.repository.ConfigRepositoryImpl
import com.yjotdev.login.data.repository.NotificationRepositoryImpl
import com.yjotdev.login.data.repository.PaymentRepositoryImpl
import com.yjotdev.login.domain.repository.ConfigRepository
import com.yjotdev.login.domain.repository.UserRepository
import com.yjotdev.login.domain.repository.EmailRepository
import com.yjotdev.login.domain.repository.NotificationRepository
import com.yjotdev.login.domain.repository.PaymentRepository
import com.yjotdev.login.domain.repository.StringRepository

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class DiModules {

    // --- BINDINGS (Abstracciones) ---
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        impl: EmailRepositoryImpl
    ): EmailRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(
        impl: PaymentRepositoryImpl
    ): PaymentRepository

    @Binds
    @Singleton
    abstract fun bindStringRepository(
        impl: StringRepositoryImpl
    ): StringRepository

    @Binds
    @Singleton
    abstract fun bindConfigRepository(
        impl: ConfigRepositoryImpl
    ): ConfigRepository

    // --- PROVIDERS (Instancias externas) ---
    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(retrofitBuilder: RetrofitBuilder): Retrofit {
            return retrofitBuilder.getRetrofitInstance()
        }

        @Provides
        @Singleton
        fun provideUserService(retrofit: Retrofit): UserService {
            return retrofit.create(UserService::class.java)
        }

        @Provides
        @Singleton
        fun provideEmailService(retrofit: Retrofit): EmailService {
            return retrofit.create(EmailService::class.java)
        }

        @Provides
        @Singleton
        fun provideNotificationService(retrofit: Retrofit): NotificationService {
            return retrofit.create(NotificationService::class.java)
        }

        @Provides
        @Singleton
        fun providePaymentService(retrofit: Retrofit): PaymentService {
            return retrofit.create(PaymentService::class.java)
        }
    }
}