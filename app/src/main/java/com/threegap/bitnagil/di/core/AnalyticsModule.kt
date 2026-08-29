package com.threegap.bitnagil.di.core

import android.content.Context
import com.threegap.bitnagil.analytics.AnalyticsLogger
import com.threegap.bitnagil.analytics.meta.MetaAnalyticsLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalyticsLogger(@ApplicationContext context: Context): AnalyticsLogger =
        MetaAnalyticsLogger(context)
}
