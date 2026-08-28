package com.threegap.bitnagil.di.presentation

import com.threegap.bitnagil.presentation.util.analytics.AnalyticsLogger
import com.threegap.bitnagil.util.analytics.MetaAnalyticsLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsLoggerModule {
    @Binds
    @Singleton
    abstract fun bindAnalyticsLogger(impl: MetaAnalyticsLogger): AnalyticsLogger
}
