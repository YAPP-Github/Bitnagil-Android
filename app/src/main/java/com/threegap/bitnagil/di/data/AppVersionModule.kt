package com.threegap.bitnagil.di.data

import com.threegap.bitnagil.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppVersionModule {

    @Provides
    @Named("versionMajor")
    fun provideVersionMajor(): Int = BuildConfig.VERSION_MAJOR

    @Provides
    @Named("versionMinor")
    fun provideVersionMinor(): Int = BuildConfig.VERSION_MINOR

    @Provides
    @Named("versionPatch")
    fun provideVersionPatch(): Int = BuildConfig.VERSION_PATCH
}
