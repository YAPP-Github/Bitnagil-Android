package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.auth.service.AuthService
import com.threegap.bitnagil.data.emotion.service.EmotionService
import com.threegap.bitnagil.data.onboarding.service.OnBoardingService
import com.threegap.bitnagil.data.recommendroutine.service.RecommendRoutineService
import com.threegap.bitnagil.data.routine.service.RoutineService
import com.threegap.bitnagil.data.user.service.UserService
import com.threegap.bitnagil.data.version.service.VersionService
import com.threegap.bitnagil.data.writeroutine.service.WriteRoutineService
import com.threegap.bitnagil.di.core.Auth
import com.threegap.bitnagil.di.core.NoneAuth
import com.threegap.bitnagil.network.token.ReissueService
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

    @Provides
    @Singleton
    fun providerOnBoardingService(@Auth retrofit: Retrofit): OnBoardingService =
        retrofit.create(OnBoardingService::class.java)

    @Provides
    @Singleton
    fun provideRoutineService(@Auth retrofit: Retrofit): RoutineService =
        retrofit.create(RoutineService::class.java)

    @Provides
    @Singleton
    fun providerEmotionService(@Auth retrofit: Retrofit): EmotionService =
        retrofit.create(EmotionService::class.java)

    @Provides
    @Singleton
    fun providerWriteRoutineService(@Auth retrofit: Retrofit): WriteRoutineService =
        retrofit.create(WriteRoutineService::class.java)

    @Provides
    @Singleton
    fun provideReissueService(@NoneAuth retrofit: Retrofit): ReissueService =
        retrofit.create(ReissueService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@Auth retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideRecommendRoutineService(@Auth retrofit: Retrofit): RecommendRoutineService =
        retrofit.create(RecommendRoutineService::class.java)

    @Provides
    @Singleton
    fun provideVersionService(@NoneAuth retrofit: Retrofit): VersionService =
        retrofit.create(VersionService::class.java)
}
