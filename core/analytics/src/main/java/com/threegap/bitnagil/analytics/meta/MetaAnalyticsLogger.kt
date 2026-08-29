package com.threegap.bitnagil.analytics.meta

import android.content.Context
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.threegap.bitnagil.analytics.AnalyticsLogger

class MetaAnalyticsLogger(private val context: Context) : AnalyticsLogger {

    private val logger by lazy { AppEventsLogger.newLogger(context) }

    override fun logOnBoardingCompleted() {
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_TUTORIAL)
    }
}
