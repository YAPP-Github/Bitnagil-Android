package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.auth.datasource.AuthLocalDataSource
import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.datasourceimpl.AuthLocalDataSourceImpl
import com.threegap.bitnagil.data.auth.datasourceimpl.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(authDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource
}
