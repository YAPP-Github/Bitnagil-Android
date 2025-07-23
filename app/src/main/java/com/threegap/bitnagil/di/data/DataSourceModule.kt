package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.auth.datasource.AuthLocalDataSource
import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.datasourceimpl.AuthLocalDataSourceImpl
import com.threegap.bitnagil.data.auth.datasourceimpl.AuthRemoteDataSourceImpl
import com.threegap.bitnagil.data.onboarding.datasource.OnBoardingDataSource
import com.threegap.bitnagil.data.onboarding.datasourceImpl.OnBoardingDataSourceImpl
import com.threegap.bitnagil.data.writeroutine.datasource.WriteRoutineDataSource
import com.threegap.bitnagil.data.writeroutine.datasourceImpl.WriteRoutineDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    @Singleton
    abstract fun bindOnBoardingDataSource(onBoardingDataSourceImpl: OnBoardingDataSourceImpl): OnBoardingDataSource

    @Binds
    @Singleton
    abstract fun bindWriteRoutineDataSource(writeRoutineDataSourceImpl: WriteRoutineDataSourceImpl): WriteRoutineDataSource
}
