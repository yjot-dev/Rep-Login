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
import com.yjotdev.login.data.remote.api.EmailApi
import com.yjotdev.login.data.remote.api.NotificationApi
import com.yjotdev.login.data.remote.api.PaymentApi
import com.yjotdev.login.data.remote.api.UserApi
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
        fun provideUserApi(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
        }

        @Provides
        @Singleton
        fun provideEmailApi(retrofit: Retrofit): EmailApi {
            return retrofit.create(EmailApi::class.java)
        }

        @Provides
        @Singleton
        fun provideNotificationApi(retrofit: Retrofit): NotificationApi {
            return retrofit.create(NotificationApi::class.java)
        }

        @Provides
        @Singleton
        fun providePaymentApi(retrofit: Retrofit): PaymentApi {
            return retrofit.create(PaymentApi::class.java)
        }
    }
}