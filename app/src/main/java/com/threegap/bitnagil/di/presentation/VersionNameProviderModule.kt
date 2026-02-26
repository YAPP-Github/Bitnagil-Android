package com.threegap.bitnagil.di.presentation

import com.threegap.bitnagil.presentation.util.version.AndroidApplicationVersionNameProvider
import com.threegap.bitnagil.presentation.util.version.VersionNameProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VersionNameProviderModule {
    @Binds
    @Singleton
    abstract fun bindVersionNameProvider(androidApplicationVersionNameProvider: AndroidApplicationVersionNameProvider): VersionNameProvider
}
