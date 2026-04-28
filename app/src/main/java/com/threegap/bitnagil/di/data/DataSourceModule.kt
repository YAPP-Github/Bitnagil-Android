package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.data.address.datasource.AddressDataSource
import com.threegap.bitnagil.data.address.datasource.LocationDataSource
import com.threegap.bitnagil.data.address.datasourceImpl.AddressDataSourceImpl
import com.threegap.bitnagil.data.address.datasourceImpl.LocationDataSourceImpl
import com.threegap.bitnagil.data.auth.datasource.AuthLocalDataSource
import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.datasourceimpl.AuthLocalDataSourceImpl
import com.threegap.bitnagil.data.auth.datasourceimpl.AuthRemoteDataSourceImpl
import com.threegap.bitnagil.data.emotion.datasource.EmotionLocalDataSource
import com.threegap.bitnagil.data.emotion.datasource.EmotionRemoteDataSource
import com.threegap.bitnagil.data.emotion.datasourceImpl.EmotionLocalDataSourceImpl
import com.threegap.bitnagil.data.emotion.datasourceImpl.EmotionRemoteDataSourceImpl
import com.threegap.bitnagil.data.file.datasource.FileDataSource
import com.threegap.bitnagil.data.file.datasourceImpl.FileDataSourceImpl
import com.threegap.bitnagil.data.onboarding.datasource.OnBoardingDataSource
import com.threegap.bitnagil.data.onboarding.datasourceImpl.OnBoardingDataSourceImpl
import com.threegap.bitnagil.data.recommendroutine.datasource.RecommendRoutineDataSource
import com.threegap.bitnagil.data.recommendroutine.datasourceImpl.RecommendRoutineDataSourceImpl
import com.threegap.bitnagil.data.report.datasource.ReportDataSource
import com.threegap.bitnagil.data.report.datasourceImpl.ReportDataSourceImpl
import com.threegap.bitnagil.data.routine.datasource.RoutineLocalDataSource
import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.datasourceImpl.RoutineLocalDataSourceImpl
import com.threegap.bitnagil.data.routine.datasourceImpl.RoutineRemoteDataSourceImpl
import com.threegap.bitnagil.data.user.datasource.UserLocalDataSource
import com.threegap.bitnagil.data.user.datasource.UserRemoteDataSource
import com.threegap.bitnagil.data.user.datasourceImpl.UserLocalDataSourceImpl
import com.threegap.bitnagil.data.user.datasourceImpl.UserRemoteDataSourceImpl
import com.threegap.bitnagil.data.version.datasource.VersionDataSource
import com.threegap.bitnagil.data.version.datasourceImpl.VersionDataSourceImpl
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
    abstract fun bindRoutineRemoteDataSource(routineDataSourceImpl: RoutineRemoteDataSourceImpl): RoutineRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRoutineLocalDataSource(impl: RoutineLocalDataSourceImpl): RoutineLocalDataSource

    @Binds
    @Singleton
    abstract fun bindEmotionLocalDataSource(emotionLocalDataSourceImpl: EmotionLocalDataSourceImpl): EmotionLocalDataSource

    @Binds
    @Singleton
    abstract fun bindEmotionRemoteDataSource(emotionRemoteDataSourceImpl: EmotionRemoteDataSourceImpl): EmotionRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRecommendRoutineDataSource(recommendRoutineDataSourceImpl: RecommendRoutineDataSourceImpl): RecommendRoutineDataSource

    @Binds
    @Singleton
    abstract fun bindVersionDataSource(versionDataSourceImpl: VersionDataSourceImpl): VersionDataSource

    @Binds
    @Singleton
    abstract fun bindLocationDataSource(locationDataSourceImpl: LocationDataSourceImpl): LocationDataSource

    @Binds
    @Singleton
    abstract fun bindAddressDataSource(addressDataSourceImpl: AddressDataSourceImpl): AddressDataSource

    @Binds
    @Singleton
    abstract fun bindFileDataSource(impl: FileDataSourceImpl): FileDataSource

    @Binds
    @Singleton
    abstract fun bindReportDataSource(impl: ReportDataSourceImpl): ReportDataSource
}
