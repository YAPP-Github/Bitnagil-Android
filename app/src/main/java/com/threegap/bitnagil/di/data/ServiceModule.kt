package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.address.service.AddressService
import com.threegap.bitnagil.data.auth.service.AuthService
import com.threegap.bitnagil.data.auth.service.LoginService
import com.threegap.bitnagil.data.emotion.service.EmotionService
import com.threegap.bitnagil.data.file.service.FileService
import com.threegap.bitnagil.data.onboarding.service.OnBoardingService
import com.threegap.bitnagil.data.recommendroutine.service.RecommendRoutineService
import com.threegap.bitnagil.data.report.service.ReportService
import com.threegap.bitnagil.data.routine.service.RoutineService
import com.threegap.bitnagil.data.user.service.UserService
import com.threegap.bitnagil.data.version.service.VersionService
import com.threegap.bitnagil.network.Auth
import com.threegap.bitnagil.network.Kakao
import com.threegap.bitnagil.network.NoneAuth
import com.threegap.bitnagil.network.token.ReissueService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(@Auth retrofit: Retrofit): AuthService = retrofit.create()

    @Provides
    @Singleton
    fun provideLoginService(@NoneAuth retrofit: Retrofit): LoginService = retrofit.create()

    @Provides
    @Singleton
    fun providerOnBoardingService(@Auth retrofit: Retrofit): OnBoardingService = retrofit.create()

    @Provides
    @Singleton
    fun provideRoutineService(@Auth retrofit: Retrofit): RoutineService = retrofit.create()

    @Provides
    @Singleton
    fun providerEmotionService(@Auth retrofit: Retrofit): EmotionService = retrofit.create()

    @Provides
    @Singleton
    fun provideReissueService(@NoneAuth retrofit: Retrofit): ReissueService = retrofit.create()

    @Provides
    @Singleton
    fun provideUserService(@Auth retrofit: Retrofit): UserService = retrofit.create()

    @Provides
    @Singleton
    fun provideRecommendRoutineService(@Auth retrofit: Retrofit): RecommendRoutineService = retrofit.create()

    @Provides
    @Singleton
    fun provideVersionService(@NoneAuth retrofit: Retrofit): VersionService = retrofit.create()

    @Provides
    @Singleton
    fun provideAddressService(@Kakao retrofit: Retrofit): AddressService = retrofit.create()

    @Provides
    @Singleton
    fun provideFileService(@Auth retrofit: Retrofit): FileService = retrofit.create()

    @Provides
    @Singleton
    fun provideReportService(@Auth retrofit: Retrofit): ReportService = retrofit.create()
}
