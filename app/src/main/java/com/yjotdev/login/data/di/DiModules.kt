package com.yjotdev.login.data.di

import android.content.Context
import androidx.room.Room
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.Module
import dagger.Binds
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import retrofit2.Retrofit
import com.yjotdev.login.data.repository.UserApiRepositoryImpl
import com.yjotdev.login.data.repository.EmailApiRepositoryImpl
import com.yjotdev.login.data.repository.StringRepositoryImpl
import com.yjotdev.login.data.remote.network.RetrofitBuilder
import com.yjotdev.login.data.remote.api.EmailApi
import com.yjotdev.login.data.remote.api.NotificationApi
import com.yjotdev.login.data.remote.api.PaymentApi
import com.yjotdev.login.data.remote.api.UserApi
import com.yjotdev.login.data.local.database.GupnDatabase
import com.yjotdev.login.data.local.dao.UserDao
import com.yjotdev.login.data.local.dao.NotificationDao
import com.yjotdev.login.data.local.dao.PaymentDao
import com.yjotdev.login.data.repository.ConfigRepositoryImpl
import com.yjotdev.login.data.repository.NotificationApiRepositoryImpl
import com.yjotdev.login.data.repository.NotificationDaoRepositoryImpl
import com.yjotdev.login.data.repository.PaymentApiRepositoryImpl
import com.yjotdev.login.data.repository.PaymentDaoRepositoryImpl
import com.yjotdev.login.data.repository.UserDaoRepositoryImpl
import com.yjotdev.login.domain.repository.ConfigRepository
import com.yjotdev.login.domain.repository.UserApiRepository
import com.yjotdev.login.domain.repository.EmailApiRepository
import com.yjotdev.login.domain.repository.NotificationApiRepository
import com.yjotdev.login.domain.repository.NotificationDaoRepository
import com.yjotdev.login.domain.repository.PaymentApiRepository
import com.yjotdev.login.domain.repository.PaymentDaoRepository
import com.yjotdev.login.domain.repository.StringRepository
import com.yjotdev.login.domain.repository.UserDaoRepository

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class DiModules {

    // --- BINDINGS (Abstracciones) ---
    @Binds
    @Singleton
    abstract fun bindUserApiRepository(
        impl: UserApiRepositoryImpl
    ): UserApiRepository

    @Binds
    @Singleton
    abstract fun bindUserDaoRepository(
        impl: UserDaoRepositoryImpl
    ): UserDaoRepository

    @Binds
    @Singleton
    abstract fun bindEmailApiRepository(
        impl: EmailApiRepositoryImpl
    ): EmailApiRepository

    @Binds
    @Singleton
    abstract fun bindNotificationApiRepository(
        impl: NotificationApiRepositoryImpl
    ): NotificationApiRepository

    @Binds
    @Singleton
    abstract fun bindNotificationDaoRepository(
        impl: NotificationDaoRepositoryImpl
    ): NotificationDaoRepository

    @Binds
    @Singleton
    abstract fun bindPaymentApiRepository(
        impl: PaymentApiRepositoryImpl
    ): PaymentApiRepository

    @Binds
    @Singleton
    abstract fun bindPaymentDaoRepository(
        impl: PaymentDaoRepositoryImpl
    ): PaymentDaoRepository

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

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): GupnDatabase =
            Room.databaseBuilder(
                context,
                GupnDatabase::class.java,
                GupnDatabase.NAME
            ).fallbackToDestructiveMigration(true).build()

        @Provides
        @Singleton
        fun provideUserDao(database: GupnDatabase): UserDao =
            database.userDao()

        @Provides
        @Singleton
        fun provideNotificationDao(database: GupnDatabase): NotificationDao =
            database.notificationDao()

        @Provides
        @Singleton
        fun providePaymentDao(database: GupnDatabase): PaymentDao =
            database.paymentDao()
    }
}