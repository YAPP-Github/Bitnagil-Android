package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.auth.repositoryimpl.AuthRepositoryImpl
import com.threegap.bitnagil.data.onboarding.repositoryImpl.OnBoardingRepositoryImpl
import com.threegap.bitnagil.data.routine.repositoryImpl.RoutineRepositoryImpl
import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindOnBoardingRepository(onBoardingRepositoryImpl: OnBoardingRepositoryImpl): OnBoardingRepository

    @Binds
    @Singleton
    abstract fun bindRoutineRepository(routineRepositoryImpl: RoutineRepositoryImpl): RoutineRepository
}
