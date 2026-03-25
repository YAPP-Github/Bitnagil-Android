package com.threegap.bitnagil.di.presentation

import com.threegap.bitnagil.presentation.util.version.VersionNameProvider
import com.threegap.bitnagil.util.version.PackageManagerVersionNameProvider
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
    abstract fun bindVersionNameProvider(impl: PackageManagerVersionNameProvider): VersionNameProvider
}
