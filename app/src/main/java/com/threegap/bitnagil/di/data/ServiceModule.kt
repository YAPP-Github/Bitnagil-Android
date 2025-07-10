package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.auth.service.AuthService
import com.threegap.bitnagil.di.core.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(@Auth retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
