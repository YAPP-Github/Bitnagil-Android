package com.threegap.bitnagil.util.analytics

import android.content.Context
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.threegap.bitnagil.presentation.util.analytics.AnalyticsLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetaAnalyticsLogger @Inject constructor(
    @ApplicationContext private val context: Context,
) : AnalyticsLogger {

    private val logger by lazy { AppEventsLogger.newLogger(context) }

    override fun logOnBoardingCompleted() {
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION)
    }
}
