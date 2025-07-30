package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.auth.repositoryimpl.AuthRepositoryImpl
import com.threegap.bitnagil.data.emotion.repositoryImpl.EmotionRepositoryImpl
import com.threegap.bitnagil.data.onboarding.repositoryImpl.OnBoardingRepositoryImpl
import com.threegap.bitnagil.data.recommendroutine.repositoryImpl.RecommendRoutineRepositoryImpl
import com.threegap.bitnagil.data.routine.repositoryImpl.RoutineRepositoryImpl
import com.threegap.bitnagil.data.user.repositoryImpl.UserRepositoryImpl
import com.threegap.bitnagil.data.writeroutine.repositoryImpl.WriteRoutineRepositoryImpl
import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import com.threegap.bitnagil.domain.recommendroutine.repository.RecommendRoutineRepository
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import com.threegap.bitnagil.domain.user.repository.UserRepository
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
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

    @Binds
    @Singleton
    abstract fun bindEmotionRepository(emotionRepositoryImpl: EmotionRepositoryImpl): EmotionRepository

    @Binds
    @Singleton
    abstract fun bindWriteRoutineRepository(writeRoutineRepositoryImpl: WriteRoutineRepositoryImpl): WriteRoutineRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindRecommendRoutineRepository(recommendRoutineRepositoryImpl: RecommendRoutineRepositoryImpl): RecommendRoutineRepository
}
